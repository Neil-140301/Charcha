package `in`.neil.charcha.ui.components

import `in`.neil.charcha.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun AuthorInfo(name: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(40.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://avatars.dicebear.com/api/adventurer-neutral/$name.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .crossfade(true)
                    .build(),
                contentDescription = "$name avatar",
                error = painterResource(R.drawable.ic_broken_img),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = name, fontSize = 18.sp)
            Text(text = "2 hours ago", fontSize = 10.sp, color = Color.Gray)
        }
    }
}