package com.venanciob.dam.pmdm.tarjeta_de_presentacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
fun Tarjeta(){
    val imagen = painterResource(id = R.drawable.img)
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = imagen, contentDescription = null)

            Text(
                text = "Adrian Gibu",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold)

            Text(
                text = "Estudiante de DAM",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4682F3)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "+34 612 345 678",
                    fontSize = 20.sp)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "+34 612 345 678",
                    fontSize = 20.sp)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "+34 612 345 678",
                    fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTarjeta(){
    Tarjeta()
}