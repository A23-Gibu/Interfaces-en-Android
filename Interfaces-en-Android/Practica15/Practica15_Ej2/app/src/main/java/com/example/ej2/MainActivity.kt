package com.example.ej2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

data class Tarea(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean
)

val listaTareasInicial = listOf(
    Tarea("13", "Práctica 13", "Material 3", true),
    Tarea("14", "Práctica 14", "Animaciones", true),
    Tarea("15", "Práctica 15", "Arquitectura de Compose", false)
)

sealed interface Destino {
    data object Lista : Destino
    data class Detalle(val id: String) : Destino
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppMiAula()
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

    // Movimiento suave de las nubes impulsadas por la brisa
    val derivaNubes by infiniteTransition.animateFloat(
        initialValue = -35f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "derivaNubes"
    )

    // Balanceo sutil de la hierba con el viento
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
                // Cielo azul intenso y limpio estilo estepa mongola
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1565C0), // Azul cobalto alto
                        Color(0xFF42A5F5), // Azul cielo brillante
                        Color(0xFF90CAF9), // Azul suave medio
                        Color(0xFFE3F2FD)  // Horizonte luminoso
                    )
                )
            )
    ) {
        val w = size.width
        val h = size.height

        // 1. Sol alto y limpio con resplandor suave
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

        // 2. Nubes amplias y esponjosas típicas de cielos abiertos
        dibujarNubeEstepa(Offset(w * 0.25f + derivaNubes, h * 0.16f), 1.15f)
        dibujarNubeEstepa(Offset(w * 0.72f - derivaNubes * 0.7f, h * 0.27f), 0.85f)
        dibujarNubeEstepa(Offset(w * 0.42f + derivaNubes * 0.5f, h * 0.37f), 0.70f)

        // 3. Colina lejana (montañas suaves verde azulado)
        val colinaLejana = Path().apply {
            moveTo(0f, h * 0.62f)
            cubicTo(
                w * 0.28f, h * 0.51f,
                w * 0.62f, h * 0.66f,
                w, h * 0.55f
            )
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

        // 4. Colina intermedia (pradera verde esmeralda ondulada)
        val colinaMedia = Path().apply {
            moveTo(0f, h * 0.68f)
            cubicTo(
                w * 0.35f, h * 0.76f,
                w * 0.68f, h * 0.58f,
                w, h * 0.67f
            )
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

        // 5. Colina en primer plano (verde vibrante soleado)
        val colinaPrimerPlano = Path().apply {
            moveTo(0f, h * 0.79f)
            cubicTo(
                w * 0.30f, h * 0.71f,
                w * 0.72f, h * 0.85f,
                w, h * 0.76f
            )
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

        // 6. Briznas de hierba meciéndose en primer plano
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

    // Base plana y sombreada típica de cúmulos en cielos limpios
    drawCircle(sombra, radius = 24.dp.toPx() * escala, center = Offset(centro.x - 22.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))
    drawCircle(sombra, radius = 26.dp.toPx() * escala, center = Offset(centro.x + 18.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))

    // Parte superior luminosa
    drawCircle(blancoNube, radius = 32.dp.toPx() * escala, center = Offset(centro.x, centro.y - 6.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 24.dp.toPx() * escala, center = Offset(centro.x - 28.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 25.dp.toPx() * escala, center = Offset(centro.x + 28.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 18.dp.toPx() * escala, center = Offset(centro.x - 48.dp.toPx() * escala, centro.y + 6.dp.toPx() * escala))
    drawCircle(blancoNube, radius = 19.dp.toPx() * escala, center = Offset(centro.x + 48.dp.toPx() * escala, centro.y + 6.dp.toPx() * escala))
}

// ============================================================================
// GESTIÓN DE NAVEGACIÓN (BACK STACK) Y ESTADO GLOBAL
// ============================================================================
@Composable
fun AppMiAula() {
    val backStack = remember { mutableStateListOf<Destino>(Destino.Lista) }
    val tareas = remember { mutableStateListOf<Tarea>().apply { addAll(listaTareasInicial) } }

    when (val destinoActual = backStack.lastOrNull() ?: Destino.Lista) {
        is Destino.Lista -> {
            PantallaLista(
                tareas = tareas,
                onAbrirTarea = { id ->
                    backStack.add(Destino.Detalle(id))
                }
            )
        }
        is Destino.Detalle -> {
            BackHandler {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.size - 1)
                }
            }
            val tarea = tareas.find { it.id == destinoActual.id } ?: tareas.first()
            PantallaDetalle(
                tarea = tarea,
                onVolver = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                },
                onCambiarEstado = { id, nuevaCompletada ->
                    val index = tareas.indexOfFirst { it.id == id }
                    if (index != -1) {
                        tareas[index] = tareas[index].copy(completada = nuevaCompletada)
                    }
                }
            )
        }
    }
}

// ============================================================================
// PANTALLA 1: LISTA DE TAREAS SOBRE PAISAJE NATURAL
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    tareas: List<Tarea>,
    onAbrirTarea: (String) -> Unit
) {
    var mostrarAvisoFab by remember { mutableStateOf(false) }

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
                                text = "Mi Aula",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
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
                FloatingActionButton(
                    onClick = { mostrarAvisoFab = !mostrarAvisoFab },
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir tarea")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                // Aviso animado al pulsar el FAB
                AnimatedVisibility(
                    visible = mostrarAvisoFab,
                    enter = fadeIn(tween(350)) + expandVertically(),
                    exit = fadeOut(tween(250)) + shrinkVertically()
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1B5E20).copy(alpha = 0.90f),
                        border = BorderStroke(1.dp, Color(0xFFA5D6A7)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = Color(0xFFC8E6C9),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Añadir tarea (modo demostrativo)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }

                // Lista vertical de tarjetas translúcidas
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(tareas, key = { it.id }) { tarea ->
                        TarjetaTarea(tarea = tarea, onClick = { onAbrirTarea(tarea.id) })
                    }
                }
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
// PANTALLA 2: DETALLE DE TAREA CON ANIMACIÓN DE COLOR Y ESTADO
// ============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    tarea: Tarea,
    onVolver: () -> Unit,
    onCambiarEstado: (String, Boolean) -> Unit
) {
    // Animación fluida del fondo de la tarjeta al completar/pendiente
    val colorContenedor by animateColorAsState(
        targetValue = if (tarea.completada) Color(0xFFE8F5E9).copy(alpha = 0.95f)
        else Color.White.copy(alpha = 0.90f),
        animationSpec = tween(durationMillis = 450),
        label = "colorFondoDetalle"
    )

    val colorBorde by animateColorAsState(
        targetValue = if (tarea.completada) Color(0xFF43A047) else Color(0xFF90CAF9),
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
                                    if (tarea.completada) Color(0xFFC8E6C9) else Color(0xFFE3F2FD)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = if (tarea.completada) Color(0xFF2E7D32) else Color(0xFF1565C0),
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = tarea.titulo,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF102A43)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = tarea.descripcion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF486581)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Insignia de estado
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(
                                    if (tarea.completada) Color(0xFFC8E6C9) else Color(0xFFFFEBEE)
                                )
                                .padding(horizontal = 18.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (tarea.completada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (tarea.completada) Color(0xFF1B5E20) else Color(0xFFC62828),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (tarea.completada) "Completada" else "Pendiente",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (tarea.completada) Color(0xFF1B5E20) else Color(0xFFC62828)
                            )
                        }
                    }
                }

                // Botón de acción principal
                if (!tarea.completada) {
                    Button(
                        onClick = { onCambiarEstado(tarea.id, true) },
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
                        onClick = { onCambiarEstado(tarea.id, false) },
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

@Preview(name = "Lista - Colinas de Mongolia", showBackground = true)
@Composable
fun PreviewPantallaLista() {
    MaterialTheme {
        PantallaLista(tareas = listaTareasInicial, onAbrirTarea = {})
    }
}

@Preview(name = "Detalle Pendiente", showBackground = true)
@Composable
fun PreviewPantallaDetallePendiente() {
    MaterialTheme {
        PantallaDetalle(
            tarea = Tarea("15", "Práctica 15", "Arquitectura de Compose", false),
            onVolver = {},
            onCambiarEstado = { _, _ -> }
        )
    }
}

@Preview(name = "Detalle Completada", showBackground = true)
@Composable
fun PreviewPantallaDetalleCompletada() {
    MaterialTheme {
        PantallaDetalle(
            tarea = Tarea("15", "Práctica 15", "Arquitectura de Compose", true),
            onVolver = {},
            onCambiarEstado = { _, _ -> }
        )
    }
}