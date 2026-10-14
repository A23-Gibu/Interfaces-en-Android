package com.venanciob.dam.pmdm.primerainterfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFE3F2FD) // Fondo azul claro suave
            ) {
                BirthdayCardEpiBlas(modifier = Modifier.padding(24.dp))
            }
        }
    }
}

@Composable
fun BirthdayCardEpiBlas(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Tarta
        Text(
            text = "🎂",
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. ¡FELIZ CUMPLEAÑOS! (Negro con negrita extra)
        Text(
            text = "¡FELIZ CUMPLEAÑOS!",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Nombre: EPI (Negro en negrita grande)
        Text(
            text = "EPI",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Mensaje original (Negro para máxima legibilidad)
        Text(
            text = "Espero que tengas un día increíble y que disfrutes mucho de tu cumpleaños.",
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // 5. Fila con emojis temáticos
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "🦆", fontSize = 34.sp)
            Spacer(modifier = Modifier.width(18.dp))
            Text(text = "🍪", fontSize = 34.sp)
            Spacer(modifier = Modifier.width(18.dp))
            Text(text = "🐦", fontSize = 34.sp)
        }

        Spacer(modifier = Modifier.height(36.dp))

        // 6. Firma: De parte de Blas (Negro)
        Text(
            text = "De parte de Blas",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE3F2FD) // Mismo fondo azul en la vista previa
    ) {
        BirthdayCardEpiBlas(modifier = Modifier.padding(24.dp))
    }
}