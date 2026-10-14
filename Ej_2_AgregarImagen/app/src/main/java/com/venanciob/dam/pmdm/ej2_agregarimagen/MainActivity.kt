package com.venanciob.dam.pmdm.ej2_agregarimagen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.ej2_agregarimagen.ui.theme.Ej2_AgregarImagenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ej2_AgregarImagenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        message = "¡Feliz Cumpleaños Epi!",
                        from = "De parte de Blas"
                    )
                }
            }
        }
    }
}

// 1. Contenedor Box: superpone la imagen de fondo y el contenido de texto
@Composable
fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
    // Se accede al recurso sin extensión (res/drawable/epi_blas)
    val image = painterResource(R.drawable.img)

    Box(modifier = modifier.fillMaxSize()) {
        // Imagen ajustada a pantalla completa y con opacidad al 50%
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F,
            modifier = Modifier.fillMaxSize()
        )
        // Texto colocado sobre la imagen
        GreetingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

// 2. Textos organizados en columna centrada
@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 42.sp,
            lineHeight = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(alignment = Alignment.CenterHorizontally) // Firma centrada (desafío de la práctica)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ej2_AgregarImagenTheme {
        GreetingImage(
            message = "¡Feliz Cumpleaños Epi!",
            from = "De parte de Blas"
        )
    }
}