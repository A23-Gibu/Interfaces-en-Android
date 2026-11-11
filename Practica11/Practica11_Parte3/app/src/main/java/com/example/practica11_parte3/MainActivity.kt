package com.example.practica11_parte3

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class para los mensajes
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
                PantallaMensajes()
            }
        }
    }
}

// Pantalla principal que contiene la lista
@Composable
fun PantallaMensajes() {
    val mensajes = listOf(
        Mensaje("Laura", "¿Qué tal ha ido la clase?"),
        Mensaje("Mario", "Ya terminé la práctica de Compose. También he añadido las imágenes y he terminado todos los ejercicios. Nos vemos mañana."),
        Mensaje("Ana", "Mañana llevo el proyecto terminado para revisarlo juntos antes de clase."),
        Mensaje("Pedro", "Nos vemos mañana a primera hora en la puerta del aula."),
        Mensaje("Sara", "He subido el ejercicio al repositorio por si queréis echarle un vistazo.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "MENSAJES INTERACTIVOS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mensajes) { mensaje ->
                TarjetaMensaje(mensaje = mensaje)
            }
        }
    }
}

// Tarjeta individual interactiva con animaciones de tamaño y color
@Composable
fun TarjetaMensaje(mensaje: Mensaje) {
    var expandido by remember { mutableStateOf(false) }

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
        val imagen = painterResource(id = R.drawable.img)
        Image(
            painter = imagen,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { expandido = !expandido }
        ) {
            Text(
                text = mensaje.autor,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                color = colorFondo,
                modifier = Modifier.animateContentSize()
            ) {
                Text(
                    text = mensaje.texto,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp),
                    maxLines = if (expandido) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

// Previews en tema claro y oscuro
@Preview(name = "Modo Claro", showBackground = true)
@Composable
fun PreviewClaro() {
    MaterialTheme {
        PantallaMensajes()
    }
}

@Preview(
    name = "Modo Oscuro",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewOscuro() {
    MaterialTheme {
        PantallaMensajes()
    }
}