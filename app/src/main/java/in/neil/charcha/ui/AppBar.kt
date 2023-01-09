package `in`.neil.charcha.ui

import `in`.neil.charcha.CharchaRoutes
import `in`.neil.charcha.R
import `in`.neil.charcha.ui.theme.Font
import `in`.neil.charcha.ui.theme.SIZE
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

@Composable
fun CharchaTopBar(
    currentScreen: CharchaRoutes,
    navigateUp: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (currentScreen == CharchaRoutes.Start) Arrangement.SpaceAround else Arrangement.Start,
            modifier = Modifier.padding(horizontal = SIZE.MEDIUM)
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
                Text(text = stringResource(id = R.string.comment_screen), fontSize = Font.lg)
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
                val borderSize = SIZE.RADIUS.toPx()
                drawLine(
                    color = if (isActive) Color.Blue else Color.White,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(SIZE.LARGE),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            color = if (isActive) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
