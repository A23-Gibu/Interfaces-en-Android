package com.venanciob.dam.pmdm.practica11_ej2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.venanciob.dam.pmdm.practica11_ej2.ui.theme.Practica11_Ej2Theme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica11_Ej2Theme {

        }
    }
}

@Composable
fun PantallaMensajes() {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "MIS MENSAJES",
            style = MaterialTheme.typography.titleLarge
         )


    }
}

@Composable
fun MensajeChat(autor: String, mensaje: String){

}

    @Preview(showBackground = true)
    @Composable
    fun PreviewPantallaMensajes(){
        PantallaMensajes()
    }
}



