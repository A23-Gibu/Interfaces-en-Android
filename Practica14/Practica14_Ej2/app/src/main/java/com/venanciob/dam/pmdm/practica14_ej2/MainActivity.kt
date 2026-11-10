package com.venanciob.dam.pmdm.practica14_ej2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class EstadoEntrenamiento {
    Normal,
    Activa
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
                    PantallaEntrenamiento()
                }
            }
        }
    }
}

@Composable
fun PantallaEntrenamiento() {
    var estado by remember {
        mutableStateOf(EstadoEntrenamiento.Normal)
    }

    val transition = updateTransition(
        targetState = estado,
        label = "entrenamiento"
    )

    val color by transition.animateColor(label = "color") { s ->
        if (s == EstadoEntrenamiento.Normal)
            MaterialTheme.colorScheme.surface
        else
            MaterialTheme.colorScheme.primaryContainer
    }

    val ancho by transition.animateDp(label = "ancho") { s ->
        if (s == EstadoEntrenamiento.Normal) 220.dp else 320.dp
    }

    val texto = if (estado == EstadoEntrenamiento.Normal)
        "DESCANSO" else "ENTRENANDO"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Surface(
            color = color,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .width(ancho)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = "Mancuerna",
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = texto,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                estado = if (estado == EstadoEntrenamiento.Normal)
                    EstadoEntrenamiento.Activa
                else
                    EstadoEntrenamiento.Normal
            }
        ) {
            Text(
                if (estado == EstadoEntrenamiento.Normal)
                    "INICIAR ENTRENAMIENTO" else "FINALIZAR"
            )
        }
    }
}

@Preview(name = "Estado Normal", showBackground = true)
@Composable
fun PreviewDescanso() {
    MaterialTheme {
        PantallaEntrenamiento()
    }
}