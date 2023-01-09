package `in`.neil.charcha

import `in`.neil.charcha.data.Post
import `in`.neil.charcha.ui.*
import `in`.neil.charcha.ui.components.ErrorScreen
import `in`.neil.charcha.ui.components.LoadingScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems


enum class CharchaRoutes {
    Start, Comments,
}

@Composable
fun CharchaApp(
    viewModel: PostViewModel = hiltViewModel(),
) {
    val dataUiState by viewModel.dataUiState.collectAsState()

    when (dataUiState) {
        is DataUiState.Success -> {
            val posts = (dataUiState as DataUiState.Success).data.collectAsLazyPagingItems()
            CharchaScreen(posts,
                activePost = viewModel.activePost,
                onCommentView = { viewModel.setCurrentPost(it) })
        }
        is DataUiState.Loading -> LoadingScreen()
        is DataUiState.Error -> ErrorScreen(retryAction = { viewModel.getAllPosts() })
    }


}

@Composable
fun CharchaScreen(
    posts: LazyPagingItems<Post>,
    activePost: Post?,
    onCommentView: (Post) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CharchaRoutes.valueOf(
        backStackEntry?.destination?.route ?: CharchaRoutes.Start.name
    )

    Scaffold(topBar = {
        CharchaTopBar(currentScreen = currentScreen) { navController.navigateUp() }
    }) {

        NavHost(
            navController = navController,
            startDestination = CharchaRoutes.Start.name,
            modifier = modifier.padding(it)
        ) {
            // start
            composable(CharchaRoutes.Start.name) {
                CharchaHome(
                    posts = posts,
                    onNavigate = { route -> navController.navigate(route) },
                    onCommentView = onCommentView
                )
            }

            composable(CharchaRoutes.Comments.name) {
                if (activePost != null) {
                    PostComments(activePost)
                }
            }
        }
    }
}
