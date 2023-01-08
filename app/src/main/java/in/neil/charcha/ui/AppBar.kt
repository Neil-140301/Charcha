package `in`.neil.charcha.ui

import `in`.neil.charcha.CharchaRoutes
import `in`.neil.charcha.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharchaTopBar(
    currentScreen: CharchaRoutes,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (currentScreen == CharchaRoutes.Start) Arrangement.SpaceAround else Arrangement.Start,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (currentScreen == CharchaRoutes.Start) {
                Tab(
                    label = stringResource(id = R.string.tab_1),
                    modifier = Modifier.weight(1f),
                    isActive = true
                )
                Tab(label = stringResource(id = R.string.tab_2), modifier = Modifier.weight(1f))
                Tab(label = stringResource(id = R.string.tab_3), modifier = Modifier.weight(1f))
            } else {
                IconButton(onClick = navigateUp) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = "go back")
                }
                Text(text = stringResource(id = R.string.comment_screen), fontSize = 20.sp)
            }
        }

    }

}

@Composable
fun Tab(label: String, modifier: Modifier = Modifier, isActive: Boolean = false) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val borderSize = 4.dp.toPx()
                drawLine(
                    color = if (isActive) Color.Blue else Color.White,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(20.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            color = if (isActive) Color.Blue else Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
