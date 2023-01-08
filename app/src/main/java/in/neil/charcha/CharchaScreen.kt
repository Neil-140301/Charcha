package `in`.neil.charcha

import `in`.neil.charcha.data.Post
import `in`.neil.charcha.ui.CharchaTopBar
import `in`.neil.charcha.ui.DataUiState
import `in`.neil.charcha.ui.PostComments
import `in`.neil.charcha.ui.PostViewModel
import `in`.neil.charcha.ui.components.ErrorScreen
import `in`.neil.charcha.ui.components.LoadingScreen
import `in`.neil.charcha.ui.components.Post
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class CharchaRoutes {
    Start, Comments,
}

@Composable
fun CharchaApp(
    viewModel: PostViewModel,
) {
    when (val dataUiState: DataUiState = viewModel.dataUiState) {

        is DataUiState.Success -> {
            val posts = remember {
                dataUiState.posts
            }

            CharchaScreen(
                posts,
                onCommentsView = { viewModel.setActivePost(it) },
                activePost = dataUiState.activePost
            )
        }
        is DataUiState.Loading -> LoadingScreen()
        is DataUiState.Error -> ErrorScreen(retryAction = { viewModel.getAllPosts() })
    }


}

@Composable
fun CharchaScreen(
    posts: List<Post>,
    activePost: Post?,
    onCommentsView: (Post) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CharchaRoutes.valueOf(
        backStackEntry?.destination?.route ?: CharchaRoutes.Start.name
    )

    Scaffold(topBar = {
        CharchaTopBar(currentScreen = currentScreen, navigateUp = { navController.navigateUp() })
    }) {

        NavHost(
            navController = navController,
            startDestination = CharchaRoutes.Start.name,
            modifier = modifier.padding(it)
        ) {
            // start
            composable(CharchaRoutes.Start.name) {
                LazyColumn {
                    items(posts, key = { p -> "${p.id}-post-${p.content}" }) { post ->
                        Post(
                            onCommentsClick = {
                                onCommentsView(post)
                                navController.navigate(CharchaRoutes.Comments.name)
                            }, data = post
                        )
                    }
                }
            }

            composable(CharchaRoutes.Comments.name) {
                if (activePost != null) {
                    PostComments(activePost)
                }
            }
        }
    }
}
