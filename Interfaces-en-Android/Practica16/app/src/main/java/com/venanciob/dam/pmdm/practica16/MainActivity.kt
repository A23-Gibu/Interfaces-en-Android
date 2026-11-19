package com.venanciob.dam.pmdm.practica16

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.venanciob.dam.pmdm.practica16.ui.theme.Practica16Theme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ============================================================================
// MODELO DE DATOS Y LISTA AMPLIADA (Para probar scroll y botón Volver Arriba)
// ============================================================================
data class Tarea(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean
)

val listaTareasInicial = listOf(
    Tarea("11", "Práctica 11", "Layouts básicos y Modifiers", true),
    Tarea("12", "Práctica 12", "Estados y recomposición", true),
    Tarea("13", "Práctica 13", "Material 3 y componentes", true),
    Tarea("14", "Práctica 14", "Animaciones y transiciones", true),
    Tarea("15", "Práctica 15", "Arquitectura y navegación en Compose", true),
    Tarea("16", "Práctica 16", "Estado avanzado, efectos y corrutinas", false),
    Tarea("17", "Práctica 17", "Consumo de APIs REST con Retrofit", false),
    Tarea("18", "Práctica 18", "Persistencia local con Room", false),
    Tarea("19", "Práctica 19", "Testing en Jetpack Compose", false),
    Tarea("20", "Proyecto Final", "Aplicación completa Mi Aula Pro", false)
)

sealed interface Destino {
    data object Lista : Destino
    data class Detalle(val id: String) : Destino
}

enum class SeccionDrawer {
    TAREAS, ACERCA_DE
}

// ============================================================================
// PARTE A: VIEWMODEL + STATEFLOW (Fuente de verdad de la pantalla)
// ============================================================================
class TareasViewModel : ViewModel() {
    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    init {
        _tareas.value = listaTareasInicial
    }

    fun cambiarEstadoTarea(id: String, nuevaCompletada: Boolean) {
        _tareas.update { listaActual ->
            listaActual.map { tarea ->
                if (tarea.id == id) tarea.copy(completada = nuevaCompletada) else tarea
            }
        }
    }
}

// ============================================================================
// ESTADO PARA EL DETALLE CON PRODUCE_STATE (Parte D)
// ============================================================================
data class DetalleUiState(
    val tarea: Tarea? = null,
    val cargando: Boolean = true
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica16Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppMiAulaPro()
                }
            }
        }
    }
}

// ============================================================================
// FONDO PAISAJÍSTICO: COLINAS VERDES Y CIELO INMENSO ESTILO MONGOLIA
// ============================================================================
@Composable
fun FondoColinasMongolia() {
    val infiniteTransition = rememberInfiniteTransition(label = "paisajeMongolia")

    val derivaNubes by infiniteTransition.animateFloat(
        initialValue = -35f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "derivaNubes"
    )

    val brisaHierba by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "brisaHierba"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1565C0),
                        Color(0xFF42A5F5),
                        Color(0xFF90CAF9),
                        Color(0xFFE3F2FD)
                    )
                )
            )
    ) {
        val w = size.width
        val h = size.height

        val centroSol = Offset(w * 0.80f, h * 0.14f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFFDE7).copy(alpha = 0.85f),
                    Color(0xFFFFF59D).copy(alpha = 0.28f),
                    Color.Transparent
                ),
                center = centroSol,
                radius = 150.dp.toPx()
            ),
            radius = 150.dp.toPx(),
            center = centroSol
        )
        drawCircle(
            color = Color(0xFFFFF9C4),
            radius = 26.dp.toPx(),
            center = centroSol
        )

        dibujarNubeEstepa(Offset(w * 0.25f + derivaNubes, h * 0.16f), 1.15f)
        dibujarNubeEstepa(Offset(w * 0.72f - derivaNubes * 0.7f, h * 0.27f), 0.85f)
        dibujarNubeEstepa(Offset(w * 0.42f + derivaNubes * 0.5f, h * 0.37f), 0.70f)

        val colinaLejana = Path().apply {
            moveTo(0f, h * 0.62f)
            cubicTo(w * 0.28f, h * 0.51f, w * 0.62f, h * 0.66f, w, h * 0.55f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            path = colinaLejana,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF4DB6AC), Color(0xFF2E7D32)),
                startY = h * 0.50f,
                endY = h * 0.85f
            )
        )

        val colinaMedia = Path().apply {
            moveTo(0f, h * 0.68f)
            cubicTo(w * 0.35f, h * 0.76f, w * 0.68f, h * 0.58f, w, h * 0.67f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            path = colinaMedia,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF66BB6A), Color(0xFF2E7D32)),
                startY = h * 0.60f,
                endY = h * 0.95f
            )
        )

        val colinaPrimerPlano = Path().apply {
            moveTo(0f, h * 0.79f)
            cubicTo(w * 0.30f, h * 0.71f, w * 0.72f, h * 0.85f, w, h * 0.76f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            path = colinaPrimerPlano,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF81C784), Color(0xFF388E3C), Color(0xFF1B5E20)),
                startY = h * 0.72f,
                endY = h
            )
        )

        val posicionesHierba = listOf(0.10f, 0.14f, 0.25f, 0.48f, 0.52f, 0.78f, 0.83f, 0.91f)
        posicionesHierba.forEachIndexed { idx, rx ->
            val baseX = w * rx
            val baseY = h * (0.86f + (idx % 3) * 0.035f)
            val alturaBrizna = (18 + (idx % 2) * 8).dp.toPx()

            drawLine(
                color = Color(0xFFA5D6A7).copy(alpha = 0.75f),
                start = Offset(baseX, baseY),
                end = Offset(baseX + brisaHierba.dp.toPx(), baseY - alturaBrizna),
                strokeWidth = 2.2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

private fun DrawScope.dibujarNubeEstepa(centro: Offset, escala: Float) {
    val sombra = Color(0xFFBBDEFB).copy(alpha = 0.65f)
    val blancoNube = Color.White.copy(alpha = 0.95f)

    drawCircle(sombra, radius = 24.dp.toPx() * escala, center = Offset(centro.x - 22.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))
    drawCircle(sombra, radius = 26.dp.toPx() * escala, center = Offset(centro.x + 18.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))

    drawCircle(blancoNube, radius = 32.dp.toPx() * escala, center = Offset(centro.x, centro.y - 6.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 24.dp.toPx() * escala, center = Offset(centro.x - 28.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 25.dp.toPx() * escala, center = Offset(centro.x + 28.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 18.dp.toPx() * escala, center = Offset(centro.x - 48.dp.toPx() * escala, centro.y + 6.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 19.dp.toPx() * escala, center = Offset(centro.x + 48.dp.toPx() * escala, centro.y + 6.dp.toPx() * escala))
}

// ============================================================================
// GESTIÓN DE NAVEGACIÓN, VIEWMODEL Y DISPOSABLE_EFFECT (Parte A y Parte E)
// ============================================================================
@Composable
fun AppMiAulaPro(
    tareasViewModel: TareasViewModel = viewModel()
) {
    // Parte E: DisposableEffect para observar eventos de ciclo de vida en Logcat
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.d("MiAula", "Lifecycle: $event")
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val backStack = remember { mutableStateListOf<Destino>(Destino.Lista) }
    // Parte A: Consumir StateFlow con collectAsStateWithLifecycle()
    val tareas by tareasViewModel.tareas.collectAsStateWithLifecycle()

    when (val destinoActual = backStack.lastOrNull() ?: Destino.Lista) {
        is Destino.Lista -> {
            PantallaPrincipalConDrawer(
                tareas = tareas,
                onAbrirTarea = { id ->
                    backStack.add(Destino.Detalle(id))
                }
            )
        }
        is Destino.Detalle -> {
            BackHandler(enabled = backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
            PantallaDetallePro(
                tareaId = destinoActual.id,
                tareas = tareas,
                onVolver = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                },
                onCambiarEstado = { id, nuevaCompletada ->
                    tareasViewModel.cambiarEstadoTarea(id, nuevaCompletada)
                }
            )
        }
    }
}

// ============================================================================
// PANTALLA 1: DRAWER + LISTA CON DERIVED_STATE_OF Y SCROLL TO TOP (Partes B y C)
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipalConDrawer(
    tareas: List<Tarea>,
    onAbrirTarea: (String) -> Unit
) {
    // Parte C: DrawerState y rememberCoroutineScope
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var seccionActual by remember { mutableStateOf(SeccionDrawer.TAREAS) }

    // Cerrar el drawer al pulsar atrás si está abierto
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    // Parte B: LazyListState + derivedStateOf para mostrar botón "Volver arriba"
    val listState = rememberLazyListState()
    val mostrarArriba by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFFF1F8E9)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0D47A1))
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = Color(0xFFC8E6C9),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Mi Aula Pro",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.TaskAlt, contentDescription = null) },
                    label = { Text("TAREAS", fontWeight = FontWeight.Bold) },
                    selected = seccionActual == SeccionDrawer.TAREAS,
                    onClick = {
                        seccionActual = SeccionDrawer.TAREAS
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFFC8E6C9),
                        selectedIconColor = Color(0xFF1B5E20),
                        selectedTextColor = Color(0xFF1B5E20)
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("ACERCA DE", fontWeight = FontWeight.Bold) },
                    selected = seccionActual == SeccionDrawer.ACERCA_DE,
                    onClick = {
                        seccionActual = SeccionDrawer.ACERCA_DE
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color(0xFFC8E6C9),
                        selectedIconColor = Color(0xFF1B5E20),
                        selectedTextColor = Color(0xFF1B5E20)
                    )
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            FondoColinasMongolia()

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = Color(0xFFC8E6C9),
                                    modifier = Modifier.size(26.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = if (seccionActual == SeccionDrawer.TAREAS) "Mis Tareas" else "Acerca de",
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        },
                        navigationIcon = {
                            // Parte C: Abrir el panel desde IconButton usando scope.launch
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir menú",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF0D47A1).copy(alpha = 0.72f),
                            titleContentColor = Color.White
                        )
                    )
                },
                floatingActionButton = {
                    // Parte B: FloatingActionButton visible solo cuando mostrarArriba es true
                    AnimatedVisibility(
                        visible = seccionActual == SeccionDrawer.TAREAS && mostrarArriba,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            containerColor = Color(0xFF2E7D32),
                            contentColor = Color.White,
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Volver arriba"
                            )
                        }
                    }
                }
            ) { paddingValues ->
                if (seccionActual == SeccionDrawer.TAREAS) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 18.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(tareas, key = { it.id }) { tarea ->
                            TarjetaTarea(tarea = tarea, onClick = { onAbrirTarea(tarea.id) })
                        }
                    }
                } else {
                    ContenidoAcercaDe(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun ContenidoAcercaDe(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.92f)),
            border = BorderStroke(1.5.dp, Color(0xFF66BB6A))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Mi Aula Pro",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF102A43)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFC8E6C9))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Práctica 16 · Desarrollo de Interfaces\nArquitectura reactiva con ViewModel, StateFlow, collectAsStateWithLifecycle, derivedStateOf, produceState y DisposableEffect.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF486581)
                )
            }
        }
    }
}

// ============================================================================
// COMPONENTE REUTILIZABLE: TARJETA DE TAREA CON ESTILO CRISTAL NATURAL
// ============================================================================
@Composable
fun TarjetaTarea(
    tarea: Tarea,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.88f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (tarea.completada) Color(0xFF66BB6A) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (tarea.completada) Color(0xFFE8F5E9) else Color(0xFFE3F2FD)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (tarea.completada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (tarea.completada) Color(0xFF2E7D32) else Color(0xFF1565C0),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF102A43)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF486581)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Abrir detalle",
                tint = Color(0xFF2E7D32),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(16.dp)
            )
        }
    }
}

// ============================================================================
// PANTALLA 2: DETALLE CON CARGA SIMULADA USANDO PRODUCE_STATE (Parte D)
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetallePro(
    tareaId: String,
    tareas: List<Tarea>,
    onVolver: () -> Unit,
    onCambiarEstado: (String, Boolean) -> Unit
) {
    // Parte D: produceState simula una carga de 800 ms ligada al id de la tarea
    val uiState by produceState(
        initialValue = DetalleUiState(cargando = true),
        key1 = tareaId
    ) {
        delay(800L)
        value = DetalleUiState(
            tarea = tareas.find { it.id == tareaId },
            cargando = false
        )
    }

    // Obtenemos la tarea actualizada en tiempo real desde el ViewModel tras finalizar la carga
    val tareaActual = tareas.find { it.id == tareaId } ?: uiState.tarea

    val colorContenedor by animateColorAsState(
        targetValue = if (tareaActual?.completada == true) Color(0xFFE8F5E9).copy(alpha = 0.95f)
        else Color.White.copy(alpha = 0.90f),
        animationSpec = tween(durationMillis = 450),
        label = "colorFondoDetalle"
    )

    val colorBorde by animateColorAsState(
        targetValue = if (tareaActual?.completada == true) Color(0xFF43A047) else Color(0xFF90CAF9),
        animationSpec = tween(durationMillis = 450),
        label = "colorBordeDetalle"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        FondoColinasMongolia()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Detalle de tarea",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0D47A1).copy(alpha = 0.72f),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            if (uiState.cargando) {
                // Estado de carga (Parte D)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.92f))
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = Color(0xFF2E7D32))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Cargando detalle...",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF102A43)
                            )
                        }
                    }
                }
            } else if (tareaActual != null) {
                // Contenido cargado (Parte D)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(28.dp)),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = colorContenedor),
                        border = BorderStroke(2.dp, colorBorde)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (tareaActual.completada) Color(0xFFC8E6C9) else Color(0xFFE3F2FD)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = if (tareaActual.completada) Color(0xFF2E7D32) else Color(0xFF1565C0),
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = tareaActual.titulo,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF102A43)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = tareaActual.descripcion,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF486581)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(
                                        if (tareaActual.completada) Color(0xFFC8E6C9) else Color(0xFFFFEBEE)
                                    )
                                    .padding(horizontal = 18.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (tareaActual.completada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = if (tareaActual.completada) Color(0xFF1B5E20) else Color(0xFFC62828),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (tareaActual.completada) "Completada" else "Pendiente",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (tareaActual.completada) Color(0xFF1B5E20) else Color(0xFFC62828)
                                )
                            }
                        }
                    }

                    if (!tareaActual.completada) {
                        Button(
                            onClick = { onCambiarEstado(tareaActual.id, true) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "MARCAR COMPLETADA",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = { onCambiarEstado(tareaActual.id, false) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(2.dp, Color(0xFF1565C0)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF1565C0)
                            )
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "MARCAR PENDIENTE",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Mi Aula Pro - Lista", showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    MaterialTheme {
        PantallaPrincipalConDrawer(tareas = listaTareasInicial, onAbrirTarea = {})
    }
}