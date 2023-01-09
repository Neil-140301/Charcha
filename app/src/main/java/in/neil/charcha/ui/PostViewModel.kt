package `in`.neil.charcha.ui

import `in`.neil.charcha.data.Post
import `in`.neil.charcha.data.PostsPageSource
import `in`.neil.charcha.data.PostsRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface DataUiState {
    data class Success(val data: Flow<PagingData<Post>>) : DataUiState
    object Error : DataUiState
    object Loading : DataUiState
}

const val PAGE_SIZE = 10

@HiltViewModel
class PostViewModel @Inject constructor(private val postsRepository: PostsRepository) :
    ViewModel() {
    private val _dataUiState = MutableStateFlow<DataUiState>(DataUiState.Loading)
    val dataUiState: StateFlow<DataUiState>
        get() = _dataUiState.asStateFlow()

    var activePost: Post? by mutableStateOf(null)

    init {
        getAllPosts()
    }

    fun setCurrentPost(data: Post) {
        activePost = data
    }

    fun getAllPosts() {
        viewModelScope.launch {
            _dataUiState.value = DataUiState.Loading
            _dataUiState.value = try {
                val response = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    PostsPageSource(postsRepository)
                }.flow

                DataUiState.Success(response)
            } catch (e: IOException) {
                DataUiState.Error
            } catch (e: HttpException) {
                DataUiState.Error
            }
        }
    }
}