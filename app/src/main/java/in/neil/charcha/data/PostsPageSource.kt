package `in`.neil.charcha.data

import androidx.paging.PagingSource
import androidx.paging.PagingState


class PostsPageSource(private val postsRepository: PostsRepository) :
    PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = postsRepository.getPage(nextPageNumber, params.loadSize)

            LoadResult.Page(
                data = response.posts,
                prevKey = params.prevKey(),
                nextKey = params.nextKey(response.total)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

fun PagingSource.LoadParams<Int>.prevKey() =
    (key?.coerceAtLeast(0) ?: 0).takeIf { it > 0 }?.minus(loadSize)?.coerceAtLeast(0)

fun PagingSource.LoadParams<Int>.nextKey(total: Int) =
    (key?.coerceAtLeast(0) ?: 0).plus(loadSize).takeIf { it <= total }