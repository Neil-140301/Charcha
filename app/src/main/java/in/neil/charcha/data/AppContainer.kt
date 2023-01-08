package `in`.neil.charcha.data

import `in`.neil.charcha.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val postsRepository: PostsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val postsRepository: PostsRepository by lazy {
        DefaultPostsRepository(retrofitService)
    }

}