package com.venanciob.dam.pmdm.practica7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.practica7.ui.theme.Practica7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica7Theme {

            }
        }
    }
}

@Composable
fun Practica7(){
    val imagen = painterResource(id = R.drawable.img)

    Column() {
        Image(painter = imagen,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Cambio de tiempo en España", Modifier.padding(16.dp), fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)

        Text(text = "España podría experimentar un importante descenso de las temperaturas en los próximos días debido a la llegada de una masa de aire frío de origen polar. Este cambio meteorológico podría dejar heladas en el interior peninsular, nevadas en zonas de montaña y temperaturas muy bajas en buena parte del país.", Modifier.padding(10.dp), textAlign = TextAlign.Justify)

        Text(text = "Según las previsiones meteorológicas, el episodio podría venir acompañado de fuertes rachas de viento, lluvias y una sensación térmica especialmente fría. Las zonas del norte y del interior serían algunas de las más afectadas, aunque el alcance dependerá de la evolución de la masa de aire frío.", Modifier.padding(10.dp), textAlign = TextAlign.Justify)
    }




}

@Preview(showBackground = true)
@Composable
fun PreviewPractica7(){
    Practica7()
}