package `in`.neil.charcha.data

import `in`.neil.charcha.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com/"

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(@Named("BASE_URL") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun providePostsRepository(apiService: ApiService): PostsRepository =
        DefaultPostsRepository(apiService)

}