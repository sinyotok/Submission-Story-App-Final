package com.aryanto.storyappfinal.core.data.network

import com.aryanto.storyappfinal.core.data.response.AddStoryResponse
import com.aryanto.storyappfinal.core.data.response.DetailResponse
import com.aryanto.storyappfinal.core.data.response.LoginResponse
import com.aryanto.storyappfinal.core.data.response.RegisterResponse
import com.aryanto.storyappfinal.core.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int?,
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetail(
        @Path("id") id: String
    ): DetailResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse

}