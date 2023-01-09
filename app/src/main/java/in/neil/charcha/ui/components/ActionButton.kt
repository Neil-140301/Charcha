package `in`.neil.charcha.ui.components

import `in`.neil.charcha.ui.theme.SIZE
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

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
        IconButton(onClick = onClick, modifier = modifier.size(SIZE.LARGE)) {
            Icon(
                icon,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                tint = if (isLiked) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
            )
        }
        Spacer(modifier = Modifier.width(SIZE.SMALL))
        Text(text = label)
    }
}