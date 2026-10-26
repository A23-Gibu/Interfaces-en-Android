package com.venanciob.dam.pmdm.practica10_parte1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                PantallaComparacionBotones()
            }
        }
    }
}

@Composable
fun PantallaComparacionBotones() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "TIPOS DE BOTÓN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 1. Botón relleno
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("ACCIÓN PRINCIPAL")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Botón elevado con sombra
        ElevatedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("BOTÓN ELEVADO")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Botón con tono suave
        FilledTonalButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("ACCIÓN SECUNDARIA")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaComparacionBotones() {
    MaterialTheme {
        PantallaComparacionBotones()
    }
}