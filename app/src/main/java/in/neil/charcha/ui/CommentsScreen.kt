package `in`.neil.charcha.ui

import `in`.neil.charcha.R
import `in`.neil.charcha.data.Comment
import `in`.neil.charcha.data.Post
import `in`.neil.charcha.ui.components.ActionButton
import `in`.neil.charcha.ui.components.AuthorInfo
import `in`.neil.charcha.ui.components.Post
import `in`.neil.charcha.ui.theme.Font
import `in`.neil.charcha.ui.theme.SIZE
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlin.random.Random

@Composable
fun PostComments(post: Post) {
    val comments = remember {
        post.comments
    }

    Column {
        Post(hideActions = true, data = post)

        // comments
        LazyColumn {
            items(comments, key = { it.id }) {
                Comment(it)
            }
        }
    }

}

@Composable
fun Comment(data: Comment, modifier: Modifier = Modifier) {
    var isLiked by rememberSaveable {
        mutableStateOf(Random.nextBoolean())
    }

    Column(
        modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    0.8f
                )
            }
            .padding(start = SIZE.PADDING, end = SIZE.PADDING, top = SIZE.PADDING)

    ) {
        //header
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            AuthorInfo(name = data.user, avatar = data.userAvatar)
            Spacer(Modifier.weight(1f))
            //more
            Icon(
                Icons.Outlined.MoreVert,
                contentDescription = "more options",
            )
        }

        // content
        Text(
            text = data.content,
            maxLines = 2,
            fontSize = Font.lg,
            modifier = Modifier.padding(vertical = SIZE.LARGE / 2)
        )

        Spacer(modifier = Modifier.height(SIZE.LARGE / 2))

        ActionButton(
            onClick = { isLiked = !isLiked },
            isLiked = isLiked,
            icon = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(
                id = R.string.likes
            ),
            label = stringResource(id = R.string.comment_like)
        )

        Spacer(modifier = Modifier.height(SIZE.LARGE / 2))
    }
}