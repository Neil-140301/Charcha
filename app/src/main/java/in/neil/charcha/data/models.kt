package `in`.neil.charcha.data

import kotlinx.serialization.Serializable

enum class PostType {
    QUESTION,
    MARKETING
}

data class Post(
    val id: Int,
    val user: String,
    val postType: PostType,
    val content: String,
    val images: List<Int> = listOf(),
    val comments: List<Comment> = listOf(),
    val likes: Int = 0
)

data class Comment(
    val user: String,
    val content: String,
    val id: Int
)

@Serializable
data class ApiPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class ApiUser(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company,
)

@Serializable
data class ApiPhoto(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)

@Serializable
data class ApiComment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
)

//temp
@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo,
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String,
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)