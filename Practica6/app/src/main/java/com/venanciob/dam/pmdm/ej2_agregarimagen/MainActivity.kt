package com.venanciob.dam.pmdm.ej2_agregarimagen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venanciob.dam.pmdm.ej2_agregarimagen.ui.theme.Ej2_AgregarImagenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ej2_AgregarImagenTheme {
                HappyBirthdayImage("Laura", "Adrian", Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun HappyBirthdayImage(name: String, from: String, modifier: Modifier = Modifier) {
    val imagen = painterResource(R.drawable.img_1)

    Box(modifier = modifier){
        Image(
            painter = imagen,
            contentDescription = null,
            alpha = 0.5F,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Happy Birthday ${name.uppercase()}",
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "From ${from.uppercase()}",
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHappyBirthdayImage(){
    HappyBirthdayImage("Laura", "Adrian", Modifier.fillMaxSize())
}