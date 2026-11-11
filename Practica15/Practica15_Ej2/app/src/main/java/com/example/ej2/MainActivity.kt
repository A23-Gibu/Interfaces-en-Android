package com.example.ej2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    tareas: List<Tarea>,
    onAbrirTarea: (String) -> Unit
) {
    var mostrarAvisoFab by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Aula") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarAvisoFab = !mostrarAvisoFab },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir tarea")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = mostrarAvisoFab,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Añadir tarea (función demostrativa)",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tareas, key = { it.id }) { tarea ->
                    TarjetaTarea(tarea = tarea, onClick = { onAbrirTarea(tarea.id) })
                }
            }
        }
    }
}

@Composable
fun TarjetaTarea(
    tarea: Tarea,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (tarea.completada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (tarea.completada) Color(0xFF2E7D32) else MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tarea.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Abrir detalle",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    tarea: Tarea,
    onVolver: () -> Unit,
    onCambiarEstado: (String, Boolean) -> Unit
) {
    val colorContenedor by animateColorAsState(
        targetValue = if (tarea.completada) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 400),
        label = "colorFondoDetalle"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de tarea") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = colorContenedor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tarea.titulo,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tarea.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (tarea.completada) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (tarea.completada) Color(0xFF2E7D32) else Color(0xFFC2185B)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (tarea.completada) "Completada" else "Pendiente",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (tarea.completada) Color(0xFF2E7D32) else Color(0xFFC2185B)
                        )
                    }
                }
            }

            if (!tarea.completada) {
                Button(
                    onClick = { onCambiarEstado(tarea.id, true) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MARCAR COMPLETADA")
                }
            } else {
                OutlinedButton(
                    onClick = { onCambiarEstado(tarea.id, false) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MARCAR PENDIENTE")
                }
            }
        }
    }
}

@Preview(name = "Lista de Tareas", showBackground = true)
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