package `in`.neil.charcha

import `in`.neil.charcha.ui.PostViewModel
import `in`.neil.charcha.ui.theme.CharchaTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharchaTheme {
                val viewModel: PostViewModel = viewModel(factory = PostViewModel.Factory)
                CharchaApp(viewModel)
            }
        }
    }
}

