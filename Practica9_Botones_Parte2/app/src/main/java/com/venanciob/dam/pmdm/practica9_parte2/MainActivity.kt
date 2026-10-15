package com.venanciob.dam.pmdm.practica9_parte2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.practica9_parte2.ui.theme.Practica9_Parte2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica9_Parte2Theme {

            }
        }
    }
}

@Composable
fun PanelAcciones(){
    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "Editar perfil",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold)

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
          Text("Guardar Cambios")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPanelAcciones() {
    PanelAcciones()
}