package com.example.practica7_ej2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MontesCuadrante() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Cuadrante(
                titulo = "1. Monte Everest (8.848 m)",
                descripcion = "Ubicado en la cordillera del Himalaya, en la frontera entre Nepal y China. Es el punto más alto de la Tierra sobre el nivel del mar.",
                color = Color(0xFF74C69D),
                modifier = Modifier.weight(1f)
            )
            Cuadrante(
                titulo = "2. K2 (8.611 m)",
                descripcion = "Situado en la cordillera del Karakórum, entre Pakistán y China. Es conocida como la 'Montaña Salvaje' por su extrema dificultad y peligrosidad técnica.",
                color = Color(0xFF95D5B2),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.weight(1f)
        ) {
            Cuadrante(
                titulo = "3. Kanchenjunga (8.586 m)",
                descripcion = "Localizado en la frontera entre la India y Nepal. Hasta 1852 se creía que era la montaña más alta del mundo.",
                color = Color(0xFFB7E4C7),
                modifier = Modifier.weight(1f)
            )
            Cuadrante(
                titulo = "4. Lhotse (8.516 m)",
                descripcion = "Conectado al Everest a través del Collado Sur en el Himalaya. Su vertiente sur es una de las paredes verticales más imponentes y desafiantes del alpinismo.",
                color = Color(0xFFD8F3DC), 
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Cuadrante(
    titulo: String,
    descripcion: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = descripcion,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview
@Composable
fun PreviewMontesCuadrante(){
    MontesCuadrante()
}