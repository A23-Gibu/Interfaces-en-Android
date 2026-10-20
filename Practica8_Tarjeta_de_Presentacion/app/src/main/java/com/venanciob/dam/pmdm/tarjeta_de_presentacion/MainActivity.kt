package com.venanciob.dam.pmdm.tarjeta_de_presentacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.tarjeta_de_presentacion.ui.theme.Tarjeta_de_PresentacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarjeta_de_PresentacionTheme {
                
            }
        }
    }
}

@Composable
fun Tarjeta() {
    val imagen = painterResource(id = R.drawable.img)

    // 1. Columna exterior con fondo verdoso y centrada horizontalmente
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2E8D4)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 2. Bloque superior: este sí lleva weight(1f) para absorber el espacio sobrante que no está siendo
        // ocupado por los otros elementos de la columna
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imagen,
                contentDescription = null,
                modifier = Modifier.size(120.dp) // Tamaño de la imagen
            )

            Text(
                text = "Adrian Gibu",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Estudiante de DAM",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E5B22), // Verde oscuro para que destaque sobre el fondo
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 3. Bloque inferior (contactos): NO lleva weight, se queda pegado abajo
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp) // Separación del borde inferior
        ) {
            // Fila 1: Teléfono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "📞", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "+34 612 345 678", fontSize = 16.sp)
            }

            // Fila 2: Red social
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "📸", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "@adriangibu.dev", fontSize = 16.sp)
            }

            // Fila 3: Correo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "✉️", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "adrian@ejemplo.com", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTarjeta(){
    Tarjeta()
}