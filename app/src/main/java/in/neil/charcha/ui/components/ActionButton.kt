package `in`.neil.charcha.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    label: String,
    modifier: Modifier = Modifier,
    isLiked: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClick, modifier = modifier.size(20.dp)) {
            Icon(
                icon,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                tint = if (isLiked) Color.Blue else Color.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}