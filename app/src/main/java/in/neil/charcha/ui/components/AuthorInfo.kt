package `in`.neil.charcha.ui.components

import `in`.neil.charcha.R
import `in`.neil.charcha.ui.theme.Font
import `in`.neil.charcha.ui.theme.SIZE
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun AuthorInfo(name: String, avatar: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        Card(
            shape = CircleShape, modifier = Modifier.size(SIZE.XLARGE)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(avatar)
                    .decoderFactory(SvgDecoder.Factory()).crossfade(true).build(),
                contentDescription = "$name avatar",
                error = painterResource(R.drawable.ic_broken_img),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(SIZE.SMALL))
        Column {
            Text(text = name, fontSize = Font.md)
            Text(
                text = stringResource(R.string.time),
                fontSize = Font.xs,
                color = MaterialTheme.colors.surface
            )
        }
    }
}