package `in`.neil.charcha.network

import `in`.neil.charcha.data.ApiComment
import `in`.neil.charcha.data.ApiPhoto
import `in`.neil.charcha.data.ApiPost
import `in`.neil.charcha.data.ApiUser
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<ApiPost>

    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("comments")
    suspend fun getComments(): List<ApiComment>

    @GET("photos")
    suspend fun getPhotos(): List<ApiPhoto>
}