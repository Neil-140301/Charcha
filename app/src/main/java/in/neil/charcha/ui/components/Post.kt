package `in`.neil.charcha.ui.components

import `in`.neil.charcha.R
import `in`.neil.charcha.data.Post
import `in`.neil.charcha.ui.theme.GhostWhite
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlin.random.Random

@Composable
fun Post(
    data: Post,
    modifier: Modifier = Modifier,
    hideActions: Boolean = false,
    onCommentsClick: () -> Unit = {}
) {
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
            .padding(
                start = 14.dp,
                end = 14.dp,
                top = 14.dp,
                bottom = if (hideActions) 15.dp else 0.dp
            )

    ) {
        //header
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            AuthorInfo(name = data.user)
            Spacer(Modifier.width(18.dp))
            //Post type
            Text(
                text = data.postType.name,
                color = Color.Blue,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Top)
                    .clip(RoundedCornerShape(4.dp))
                    .background(GhostWhite)
                    .padding(8.dp),

                )
            Spacer(modifier = Modifier.weight(0.8f))
            //more
            Icon(
                Icons.Outlined.MoreVert,
                contentDescription = "more options",
            )
        }

        // content
        Text(
            text = data.content,
            maxLines = 3,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // media
        if (data.images.isNotEmpty()) {
            PostMedia(images = data.images)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // actions
        if (!hideActions) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                ActionButton(
                    onClick = { isLiked = !isLiked },
                    isLiked = isLiked,
                    icon = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(
                        id = R.string.likes
                    ),
                    label = stringResource(id = R.string.post_likes, data.likes)
                )

                ActionButton(
                    onClick = onCommentsClick,
                    icon = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = stringResource(
                        id = R.string.comments
                    ),
                    label = stringResource(id = R.string.post_comments, data.comments.size)
                )

                ActionButton(
                    onClick = { /*TODO*/ },
                    icon = Icons.Outlined.Share,
                    contentDescription = stringResource(
                        id = R.string.share
                    ),
                    label = stringResource(id = R.string.share)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}


@Composable
fun PostMedia(images: List<Int>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(images) {
            MediaCard(it)
        }
    }
}

@Composable
fun MediaCard(id:Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .size(width = 180.dp, height = 200.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/id/$id/200/300")
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription = "",
            error = painterResource(R.drawable.ic_broken_img),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}