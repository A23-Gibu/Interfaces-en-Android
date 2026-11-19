package com.venanciob.dam.pmdm.practica9_botones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.practica9_botones.ui.theme.Practica9_botonesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica9_botonesTheme {

            }
        }
    }
}

@Composable
fun PantallaBotones(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "Botones en Compose",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp)

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {},
            modifier = Modifier.height(50.dp))
        {
            Text("Guardar", fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.height(50.dp))
        {
            Text("Cancelar", fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))

        TextButton(
            onClick = {},
            modifier = Modifier.height(50.dp))
        {
            Text("Más información", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaBotones(){
    PantallaBotones()
}