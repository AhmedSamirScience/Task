package com.application.samir.data.remote

import com.application.samir.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderApi {
    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): PostDto

    @GET("posts")
    suspend fun getPostsList(): List<PostDto>
}