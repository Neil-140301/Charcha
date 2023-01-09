package `in`.neil.charcha.data

import `in`.neil.charcha.network.ApiService
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

interface PostsRepository {
    fun getPage(startIdx: Int, limit: Int): PostsResponse
}

data class PostsResponse(
    val posts: List<Post>,
    val total: Int
)

@Singleton
class DefaultPostsRepository @Inject constructor(private val apiService: ApiService) :
    PostsRepository {
    private val allPosts: MutableState<List<Post>> = mutableStateOf(listOf())

    init {
        getAllPosts()
    }

    override fun getPage(startIdx: Int, limit: Int): PostsResponse {
        return PostsResponse(
            allPosts.value.subList(startIdx - 1, startIdx - 1 + limit),
            allPosts.value.size
        )
    }

    private fun getRandomPostType(): PostType {
        return PostType.values().random()
    }

    private fun getAllPosts() {
        runBlocking {
            var apiPosts: Deferred<List<ApiPost>>
            var apiUsers: Deferred<List<ApiUser>>
            var apiComments: Deferred<List<ApiComment>>
            coroutineScope {
                apiPosts = async { apiService.getPosts() }
                apiUsers = async { apiService.getUsers() }
                apiComments = async { apiService.getComments() }
            }

            val posts = mutableListOf<Post>()
            for (post in apiPosts.await().subList(0, apiPosts.await().size / 2)) {
                val newPost = Post(
                    postType = getRandomPostType(),
                    content = post.title,
                    likes = Random.nextInt(10, 200),
                    user = apiUsers.await().find { it.id == post.userId }?.name ?: "Anonymous",
                    images = (0..Random.nextInt(0, 10)).toList()
                        .map { "https://picsum.photos/id/${Random.nextInt(100, 200)}/200/300" },
                    comments = apiComments.await().filter { it.postId == post.id }.map { comment ->
                        val newComment = Comment(
                            user = apiUsers.await().find { it.email == comment.email }?.name
                                ?: "Anonymous",
                            content = comment.name,
                            id = comment.id
                        )

                        newComment.copy(userAvatar = "https://avatars.dicebear.com/api/adventurer-neutral/${newComment.user}.svg")
                    },
                    id = post.id
                )

                posts.add(newPost.copy(userAvatar = "https://avatars.dicebear.com/api/adventurer-neutral/${newPost.user}.svg"))
            }

            allPosts.value = posts
        }
    }
}