package com.venanciob.dam.pmdm.practica12_parte1

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.darkColorScheme

// 1. Data class Mensaje
data class Mensaje(
    val autor: String,
    val texto: String
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
                    PantallaMensajes()
                }
            }
        }
    }
}

// 2. Pantalla principal que recorre la lista con LazyColumn
@Composable
fun PantallaMensajes() {
    // Lista exacta indicada en la página 3 del documento
    val mensajes = listOf(
        Mensaje(
            "Laura",
            "¿Qué tal ha ido la clase? Espero que hayas podido terminar la práctica."
        ),
        Mensaje(
            "Mario",
            "Ya terminé la práctica de Jetpack Compose y he probado todos los ejercicios."
        ),
        Mensaje(
            "Ana",
            "Mañana llevo el proyecto para que podamos revisarlo durante la clase."
        ),
        Mensaje(
            "Pedro",
            "Nos vemos mañana. Recuerda que tenemos que terminar la interfaz."
        ),
        Mensaje(
            "Sara",
            "He subido el ejercicio al aula virtual y también he añadido las capturas."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "MIS MENSAJES",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // LazyColumn eficiente que crea elementos bajo demanda
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mensajes) { mensaje ->
                TarjetaMensaje(mensaje = mensaje)
            }
        }
    }
}

// 3. Componente reutilizable con estado, interacción y animaciones
@Composable
fun TarjetaMensaje(mensaje: Mensaje) {
    // Estado booleano independiente por cada tarjeta
    var expandido by remember { mutableStateOf(false) }

    // Color animado entre surface y primaryContainer
    val colorFondo by animateColorAsState(
        targetValue = if (expandido) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "colorMensaje"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar circular con la inicial del autor
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mensaje.autor.take(1),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Columna interactiva que alterna el estado al pulsar
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { expandido = !expandido }
        ) {
            Text(
                text = mensaje.autor,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Surface con elevación, animación de tamaño y color gradual
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = colorFondo,
                modifier = Modifier.animateContentSize()
            ) {
                Text(
                    text = mensaje.texto,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp),
                    // Si está cerrado muestra 1 línea, si está abierto muestra todo
                    maxLines = if (expandido) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

// 4. Previews claro y oscuro obligatorios
@Preview(name = "Tema Claro", showBackground = true)
@Composable
fun PreviewClaro() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PantallaMensajes()
        }
    }
}

@Preview(
    name = "Tema Oscuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewOscuro() {
    // darkColorScheme() fuerza la paleta de modo noche
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PantallaMensajes()
        }
    }
}