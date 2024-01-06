package com.application.samir.domain.repository

import com.application.samir.data.remote.dto.PostDto

interface PostRepository {
    suspend fun getPostById(postId: String): PostDto
    suspend fun getPostsList(): List<PostDto>
}