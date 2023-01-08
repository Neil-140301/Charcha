package `in`.neil.charcha.data

import `in`.neil.charcha.network.ApiService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.random.Random

interface PostsRepository {
    suspend fun getAllPosts(): List<Post>
}


class DefaultPostsRepository(private val apiService: ApiService) : PostsRepository {
    override suspend fun getAllPosts(): List<Post> {
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
            posts.add(
                Post(
                    postType = getRandomPostType(),
                    content = post.title,
                    likes = Random.nextInt(10, 200),
                    user = apiUsers.await().find { it.id == post.userId }?.name ?: "Anonymous",
                    images = (0..Random.nextInt(0, 10)).toList().map { Random.nextInt(100,200) },
                    comments = apiComments.await().filter { it.postId == post.id }.map { comment ->
                        Comment(
                            user = apiUsers.await().find { it.email == comment.email }?.name
                                ?: "Anonymous",
                            content = comment.name,
                            id = comment.id
                        )
                    },
                    id = post.id
                )
            )
        }

        return posts
    }

    private fun getRandomPostType(): PostType {
        return PostType.values().random()
    }

}