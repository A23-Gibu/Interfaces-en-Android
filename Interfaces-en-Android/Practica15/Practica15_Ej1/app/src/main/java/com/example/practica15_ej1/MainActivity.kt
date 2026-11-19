package com.example.practica15_ej1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ModoEstudioPersonalizado()
                }
            }
        }
    }
}

// ============================================================================
// FONDO DINÁMICO: CIELO RELAJANTE <-> ESPACIO EXTERIOR
// ============================================================================
@Composable
fun FondoAtmosferico(activo: Boolean) {
    // 1. Transición suave de colores del cielo (Día -> Espacio)
    val cieloSuperior by animateColorAsState(
        targetValue = if (activo) Color(0xFF040512) else Color(0xFF3898EC),
        animationSpec = tween(durationMillis = 1000),
        label = "cieloSuperior"
    )
    val cieloMedio by animateColorAsState(
        targetValue = if (activo) Color(0xFF0E1230) else Color(0xFF7BC6F9),
        animationSpec = tween(durationMillis = 1000),
        label = "cieloMedio"
    )
    val cieloInferior by animateColorAsState(
        targetValue = if (activo) Color(0xFF1C1642) else Color(0xFFD6F0FF),
        animationSpec = tween(durationMillis = 1000),
        label = "cieloInferior"
    )

    // 2. Opacidad cruzada entre elementos diurnos y nocturnos
    val opacidadDia by animateFloatAsState(
        targetValue = if (activo) 0f else 1f,
        animationSpec = tween(durationMillis = 900),
        label = "opacidadDia"
    )
    val opacidadNoche by animateFloatAsState(
        targetValue = if (activo) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "opacidadNoche"
    )

    // 3. Animaciones infinitas (movimiento de nubes, parpadeo de estrellas y rotación de galaxia)
    val infiniteTransition = rememberInfiniteTransition(label = "atmosfera")

    val derivaNubes by infiniteTransition.animateFloat(
        initialValue = -40f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "derivaNubes"
    )

    val parpadeoEstrellas1 by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "parpadeo1"
    )

    val parpadeoEstrellas2 by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "parpadeo2"
    )

    val rotacionGalaxia by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 45000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotacionGalaxia"
    )

    // Coordenadas fijas de estrellas para evitar recalculado en cada frame
    val estrellas = remember {
        listOf(
            Triple(0.12f, 0.14f, 2.5f), Triple(0.28f, 0.08f, 1.8f), Triple(0.45f, 0.18f, 3.0f),
            Triple(0.68f, 0.11f, 2.0f), Triple(0.85f, 0.16f, 2.8f), Triple(0.18f, 0.30f, 1.5f),
            Triple(0.36f, 0.26f, 2.2f), Triple(0.75f, 0.28f, 1.9f), Triple(0.90f, 0.35f, 2.6f),
            Triple(0.08f, 0.48f, 2.1f), Triple(0.24f, 0.55f, 1.6f), Triple(0.82f, 0.52f, 2.4f),
            Triple(0.15f, 0.72f, 2.8f), Triple(0.34f, 0.80f, 1.7f), Triple(0.62f, 0.76f, 2.3f),
            Triple(0.88f, 0.84f, 3.1f), Triple(0.50f, 0.88f, 1.9f), Triple(0.72f, 0.65f, 1.5f)
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(cieloSuperior, cieloMedio, cieloInferior)
                )
            )
    ) {
        val w = size.width
        val h = size.height

        // -------------------- ELEMENTOS DEL DÍA (TIERRA) --------------------
        if (opacidadDia > 0.01f) {
            // Sol cálido con halo luminoso
            val centroSol = Offset(w * 0.82f, h * 0.14f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFF59D).copy(alpha = 0.65f * opacidadDia),
                        Color(0xFFFFD54F).copy(alpha = 0.20f * opacidadDia),
                        Color.Transparent
                    ),
                    center = centroSol,
                    radius = 170.dp.toPx()
                ),
                radius = 170.dp.toPx(),
                center = centroSol
            )
            drawCircle(
                color = Color(0xFFFFEE58).copy(alpha = opacidadDia),
                radius = 38.dp.toPx(),
                center = centroSol
            )

            // Nubecillas esponjosas flotantes
            dibujarNube(
                centro = Offset(w * 0.24f + derivaNubes, h * 0.20f),
                escala = 1.0f,
                alpha = opacidadDia
            )
            dibujarNube(
                centro = Offset(w * 0.74f - derivaNubes * 0.8f, h * 0.34f),
                escala = 0.78f,
                alpha = opacidadDia * 0.9f
            )
            dibujarNube(
                centro = Offset(w * 0.28f + derivaNubes * 0.6f, h * 0.76f),
                escala = 1.15f,
                alpha = opacidadDia * 0.85f
            )
            dibujarNube(
                centro = Offset(w * 0.78f - derivaNubes * 0.5f, h * 0.85f),
                escala = 0.7f,
                alpha = opacidadDia * 0.75f
            )
        }

        // -------------------- ELEMENTOS DE LA NOCHE (ESPACIO) --------------------
        if (opacidadNoche > 0.01f) {
            // Nebulosa ambiental sutil
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF4A148C).copy(alpha = 0.28f * opacidadNoche),
                        Color(0xFF1A237E).copy(alpha = 0.12f * opacidadNoche),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.25f, h * 0.25f),
                    radius = 240.dp.toPx()
                ),
                radius = 240.dp.toPx(),
                center = Offset(w * 0.25f, h * 0.25f)
            )

            // Galaxia lejana en espiral rotando lentamente
            val centroGalaxia = Offset(w * 0.24f, h * 0.22f)
            rotate(degrees = rotacionGalaxia, pivot = centroGalaxia) {
                // Halo exterior de la galaxia
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE1BEE7).copy(alpha = 0.55f * opacidadNoche),
                            Color(0xFF7E57C2).copy(alpha = 0.25f * opacidadNoche),
                            Color.Transparent
                        ),
                        center = centroGalaxia,
                        radius = 58.dp.toPx()
                    ),
                    radius = 58.dp.toPx(),
                    center = centroGalaxia
                )
                // Brazos espirales punteados
                for (brazo in 0..1) {
                    val anguloBase = brazo * Math.PI
                    for (paso in 1..18) {
                        val radio = paso * 2.6.dp.toPx()
                        val angulo = anguloBase + paso * 0.34
                        val px = centroGalaxia.x + (cos(angulo) * radio).toFloat()
                        val py = centroGalaxia.y + (sin(angulo) * radio * 0.62f).toFloat()
                        drawCircle(
                            color = Color(0xFFD1C4E9).copy(alpha = (1f - paso / 20f) * 0.75f * opacidadNoche),
                            radius = (2.2f - paso * 0.08f).dp.toPx(),
                            center = Offset(px, py)
                        )
                    }
                }
                // Núcleo brillante de la galaxia
                drawCircle(
                    color = Color.White.copy(alpha = 0.9f * opacidadNoche),
                    radius = 6.dp.toPx(),
                    center = centroGalaxia
                )
            }

            // Estrellitas parpadeantes
            estrellas.forEachIndexed { index, (rx, ry, radioBase) ->
                val alphaEstrella = if (index % 2 == 0) parpadeoEstrellas1 else parpadeoEstrellas2
                val pos = Offset(w * rx, h * ry)
                drawCircle(
                    color = Color.White.copy(alpha = alphaEstrella * opacidadNoche),
                    radius = radioBase.dp.toPx(),
                    center = pos
                )
                // Destello en cruz para las estrellas más grandes
                if (radioBase > 2.4f) {
                    val destello = 7.dp.toPx()
                    drawLine(
                        color = Color(0xFFB3E5FC).copy(alpha = alphaEstrella * 0.7f * opacidadNoche),
                        start = Offset(pos.x - destello, pos.y),
                        end = Offset(pos.x + destello, pos.y),
                        strokeWidth = 1.dp.toPx()
                    )
                    drawLine(
                        color = Color(0xFFB3E5FC).copy(alpha = alphaEstrella * 0.7f * opacidadNoche),
                        start = Offset(pos.x, pos.y - destello),
                        end = Offset(pos.x, pos.y + destello),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
        }
    }
}

private fun DrawScope.dibujarNube(centro: Offset, escala: Float, alpha: Float) {
    val colorNube = Color.White.copy(alpha = 0.92f * alpha)
    val colorSombra = Color(0xFFE1F5FE).copy(alpha = 0.75f * alpha)

    // Sombra suave inferior
    drawCircle(colorSombra, radius = 26.dp.toPx() * escala, center = Offset(centro.x, centro.y + 6.dp.toPx() * escala))
    // Copos principales de la nube
    drawCircle(colorNube, radius = 28.dp.toPx() * escala, center = Offset(centro.x, centro.y - 6.dp.toPx() * escala))
    drawCircle(colorNube, radius = 22.dp.toPx() * escala, center = Offset(centro.x - 24.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(colorNube, radius = 24.dp.toPx() * escala, center = Offset(centro.x + 24.dp.toPx() * escala, centro.y + 2.dp.toPx() * escala))
    drawCircle(colorNube, radius = 17.dp.toPx() * escala, center = Offset(centro.x - 42.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))
    drawCircle(colorNube, radius = 18.dp.toPx() * escala, center = Offset(centro.x + 42.dp.toPx() * escala, centro.y + 8.dp.toPx() * escala))
}

// ============================================================================
// PARTE A: VERSIÓN MATERIAL (NIVEL ALTO) CON AMBIENTACIÓN
// ============================================================================
@Composable
fun ModoEstudioMaterial() {
    var activo by remember { mutableStateOf(false) }

    val colorTexto by animateColorAsState(
        targetValue = if (activo) Color.White else Color(0xFF0D2B45),
        animationSpec = tween(700),
        label = "textoMaterial"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        FondoAtmosferico(activo = activo)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MODO ESTUDIO",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colorTexto
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (activo)
                    "Inmersión profunda en el cosmos: cero distracciones"
                else
                    "Activa un entorno sin distracciones para concentrarte",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = colorTexto.copy(alpha = 0.88f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { activo = !activo },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activo) Color(0xFF651FFF) else Color(0xFF0288D1),
                    contentColor = Color.White
                )
            ) {
                Text(if (activo) "DESACTIVAR" else "ACTIVAR")
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = activo,
                enter = fadeIn(tween(500)) + expandVertically(),
                exit = fadeOut(tween(300)) + shrinkVertically()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF1E1B4B).copy(alpha = 0.85f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF9575CD))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Modo estudio activo",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// PARTE B: VERSIÓN PERSONALIZADA (MÁS CONTROL) CON AMBIENTACIÓN
// ============================================================================
@Composable
fun ModoEstudioPersonalizado() {
    var activo by remember { mutableStateOf(false) }

    val colorInicio by animateColorAsState(
        targetValue = if (activo) Color(0xFF512DA8) else Color(0xFF0288D1),
        animationSpec = tween(durationMillis = 500),
        label = "colorInicioAnimado"
    )
    val colorFin by animateColorAsState(
        targetValue = if (activo) Color(0xFF00B0FF) else Color(0xFF26C6DA),
        animationSpec = tween(durationMillis = 500),
        label = "colorFinAnimado"
    )
    val escalaBoton by animateFloatAsState(
        targetValue = if (activo) 1.06f else 1.0f,
        animationSpec = tween(durationMillis = 350),
        label = "escalaBoton"
    )
    val colorTexto by animateColorAsState(
        targetValue = if (activo) Color.White else Color(0xFF0D2B45),
        animationSpec = tween(durationMillis = 700),
        label = "colorTexto"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Capa de fondo animado (Tierra <-> Espacio)
        FondoAtmosferico(activo = activo)

        // Capa de interfaz
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Tarjeta translúcida estilo cristal (Glassmorphism)
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (activo) Color(0xFF0F1029).copy(alpha = 0.65f)
                        else Color.White.copy(alpha = 0.55f)
                    )
                    .border(
                        width = 1.dp,
                        color = if (activo) Color(0xFF7C4DFF).copy(alpha = 0.5f)
                        else Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 28.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (activo) Icons.Default.AutoAwesome else Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = if (activo) Color(0xFFFFD54F) else Color(0xFFFFA000),
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "MODO ESTUDIO",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp,
                    color = colorTexto
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (activo)
                        "Explorando el espacio exterior. Todo ruido exterior ha desaparecido."
                    else
                        "Activa un entorno sin distracciones para concentrarte",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = colorTexto.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Botón personalizado (Row + clickable + Brush.horizontalGradient)
                Row(
                    modifier = Modifier
                        .scale(escalaBoton)
                        .shadow(
                            elevation = if (activo) 14.dp else 6.dp,
                            shape = RoundedCornerShape(50.dp),
                            ambientColor = colorInicio,
                            spotColor = colorFin
                        )
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { activo = !activo }
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(colorInicio, colorFin)
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 36.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (activo) "DESACTIVAR" else "ACTIVAR",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = activo,
                    enter = fadeIn(tween(500)) + expandVertically(),
                    exit = fadeOut(tween(300)) + shrinkVertically()
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFF311B92).copy(alpha = 0.85f))
                            .border(1.dp, Color(0xFFB388FF), RoundedCornerShape(50.dp))
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Modo estudio activo",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Parte A - Material (Cielo / Espacio)", showBackground = true)
@Composable
fun PreviewModoEstudioMaterial() {
    MaterialTheme {
        ModoEstudioMaterial()
    }
}

@Preview(name = "Parte B - Personalizada (Cielo / Espacio)", showBackground = true)
@Composable
fun PreviewModoEstudioPersonalizado() {
    MaterialTheme {
        ModoEstudioPersonalizado()
    }
}