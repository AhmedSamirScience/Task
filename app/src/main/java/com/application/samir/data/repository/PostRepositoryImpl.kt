package com.application.samir.data.repository

import com.application.samir.data.remote.JsonPlaceHolderApi
import com.application.samir.data.remote.dto.PostDto
import com.application.samir.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderApi,
 ) : PostRepository {

    override suspend fun getPostById(postId: String): PostDto {
        return api.getPostById(postId)
    }

    override suspend fun getPostsList(): List<PostDto> {
        return api.getPostsList()
    }
}