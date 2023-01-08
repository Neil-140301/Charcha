package `in`.neil.charcha.ui

import `in`.neil.charcha.CharchaApplication
import `in`.neil.charcha.data.Post
import `in`.neil.charcha.data.PostsRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DataUiState {
    data class Success(val posts: List<Post>, val activePost: Post? = null) : DataUiState
    object Error : DataUiState
    object Loading : DataUiState
}

class PostViewModel(private val postsRepository: PostsRepository) : ViewModel() {
    var dataUiState: DataUiState by mutableStateOf(DataUiState.Loading)
        private set

    init {
        getAllPosts()
    }

    fun setActivePost(data: Post) {
        if (dataUiState is DataUiState.Success) {
            dataUiState = (dataUiState as DataUiState.Success).copy(activePost = data)
        }
    }

     fun getAllPosts() {
        viewModelScope.launch {
            dataUiState = DataUiState.Loading
            dataUiState = try {
                val listResult = postsRepository.getAllPosts()
                DataUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                DataUiState.Error
            } catch (e: HttpException) {
                DataUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CharchaApplication)
                val postsRepository = application.container.postsRepository
                PostViewModel(postsRepository = postsRepository)
            }
        }
    }
}