package com.venanciob.dam.pmdm.practica12_parte2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data class para las tareas (Página 10)
data class Tarea(
    val titulo: String,
    val descripcion: String
)

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
                    PantallaMiAula()
                }
            }
        }
    }
}

// 2. Pantalla principal que integra Scaffold, LazyColumn y el contador (Páginas 8-11)
@Composable
fun PantallaMiAula() {
    // Estado del contador de nuevas tareas vinculado al FAB y a reiniciar
    var nuevasTareas by remember { mutableStateOf(0) }

    // Lista fija de tareas requeridas
    val tareas = listOf(
        Tarea("Práctica 12", "Listas, Material y animaciones"),
        Tarea("Proyecto Figma", "Prototipo de interfaz"),
        Tarea("Repasar Compose", "Text, Row, Column y Box"),
        Tarea("Preparar entrega", "Capturas y documentación")
    )

    Scaffold(
        floatingActionButton = {
            // FAB obligatorio que incrementa el contador al pulsar (Página 11)
            FloatingActionButton(
                onClick = { nuevasTareas++ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir tarea"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título general
            item {
                Text(
                    text = "MI AULA",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Zona 1: Cabecera de perfil (Página 9)
            item {
                CabeceraPerfil()
            }

            // Zona 2: Imagen destacada con Box (Página 9)
            item {
                ImagenDestacada()
            }

            // Zona 3: Botonera completa y contador (Páginas 9-11)
            item {
                ZonaAcciones(
                    contador = nuevasTareas,
                    onReiniciar = { nuevasTareas = 0 }
                )
            }

            // Título de la sección de tareas
            item {
                Text(
                    text = "TAREAS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Zona 4: Lista de tarjetas interactivas (Páginas 10-11)
            items(tareas) { tarea ->
                TarjetaTarea(tarea = tarea)
            }
        }
    }
}

// Zona 1: Cabecera con avatar y datos del alumno (Página 9)
@Composable
fun CabeceraPerfil() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar circular con inicial
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "L",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Laura García",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "DAM · Desarrollo de Interfaces",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Zona 2: Contenedor Box que superpone texto sobre imagen/fondo decorativo (Página 9)
@Composable
fun ImagenDestacada() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            ),
        contentAlignment = Alignment.BottomStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.45f))
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = "DESARROLLO DE INTERFACES",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Proyecto final en Jetpack Compose",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

// Zona 3: Botonera con los 6 tipos de botones y gestión del contador (Páginas 9-11)
@Composable
fun ZonaAcciones(contador: Int, onReiniciar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Fila 1: Botón principal y OutlinedButton usando Modifier.weight(1f) para repartir el ancho al 50%
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("GUARDAR")
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CANCELAR")
            }
        }

        // Fila 2: ElevatedButton, FilledTonalButton, TextButton e IconButton
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(onClick = {}) {
                Text("EDITAR")
            }

            FilledTonalButton(onClick = {}) {
                Text("DETALLES")
            }

            TextButton(onClick = {}) {
                Text("INFO")
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Fila 3: Indicador de nuevas tareas añadidas por el FAB y botón de reinicio
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nuevas tareas: $contador",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Desactivado si el contador está en 0
            OutlinedButton(
                onClick = onReiniciar,
                enabled = contador > 0
            ) {
                Text("REINICIAR")
            }
        }
    }
}

// Zona 4: Tarjeta de tarea interactiva con animaciones de color y tamaño (Páginas 10-11)
@Composable
fun TarjetaTarea(tarea: Tarea) {
    var expandida by remember { mutableStateOf(false) }

    val colorFondo by animateColorAsState(
        targetValue = if (expandida) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "colorTarea"
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        color = colorFondo,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expandida = !expandida }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = tarea.titulo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = tarea.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expandida) Int.MAX_VALUE else 1
            )
        }
    }
}

// 3. Previews en Modo Claro y Modo Oscuro (Página 11 y 13)
@Preview(name = "Mi Aula - Claro", showBackground = true)
@Composable
fun PreviewMiAulaClaro() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PantallaMiAula()
        }
    }
}

@Preview(
    name = "Mi Aula - Oscuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMiAulaOscuro() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PantallaMiAula()
        }
    }
}