package `in`.neil.charcha.ui

import `in`.neil.charcha.CharchaRoutes
import `in`.neil.charcha.data.Post
import `in`.neil.charcha.ui.components.ErrorScreen
import `in`.neil.charcha.ui.components.LoadingScreen
import `in`.neil.charcha.ui.components.Post
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

@Composable
fun CharchaHome(
    posts: LazyPagingItems<Post>,
    onNavigate: (String) -> Unit,
    onCommentView: (Post) -> Unit,
) {
    when (posts.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen()
        }
        is LoadState.Error -> {
            ErrorScreen(noRetry = true)
        }
        else -> {
            LazyColumn {
                items(posts) { post ->
                    if (post != null) {
                        Post(
                            onCommentsClick = {
                                onCommentView(post)
                                onNavigate(CharchaRoutes.Comments.name)
                            }, data = post
                        )
                    }
                }
            }
        }
    }
}