package `in`.neil.charcha.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(
    primary = Color.Blue,
    secondary = Color.Black,
    surface = Color.Gray
)

private val LightColorPalette = lightColors(
    primary = Color.Blue,
    secondary = Color.Black,
    surface = Color.Gray,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

object SIZE {
    val XLARGE = 40.dp
    val LARGE = 20.dp
    val MEDIUM = 16.dp
    val SMALL = 8.dp

    val PADDING = 14.dp
    val RADIUS = 4.dp
    val ZERO = 0.dp

    val IMG_WIDTH = 180.dp
    val IMG_HEIGHT = 200.dp
}

object Font {
    val lg = 20.sp
    val md = 18.sp
    val sm = 12.sp
    val xs = 10.sp
}

@Composable
fun CharchaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}