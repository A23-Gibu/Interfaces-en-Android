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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.venanciob.dam.pmdm.primerainterfaz.ui.theme.PrimeraInterfazTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeraInterfazTheme {
                HappyBirthday(name = "Bertrex")
            }
        }
    }
}

@Composable
fun HappyBirthday(name: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "\uD83C\uDF82",
            fontSize = 200.sp,
            modifier = Modifier.padding(top = 50.dp, bottom = 10.dp)
        )
        Text(
            text = "Feliz cumpleaños!",
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF85C8),
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 10.dp)

        )
        Text(
            text = name.uppercase(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,
            color = Color(0xFF484AC7),
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text = "Espero que tengas un día genial y que disfrutes mucho de tu compleaños.",
            color = Color(0xFF49B763),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Row(
            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            Text(
                text = "\uD83C\uDF89",
                fontSize = 50.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = "\uD83D\uDC93",
                fontSize = 50.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = "\uD83C\uDF88",
                fontSize = 50.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        Text(
            text = "De parte de Adrian",
            color = Color(0xFF2196F3),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    HappyBirthday("Laura")
}