package com.application.samir.data.remote.dto

import com.application.samir.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("userId")
    val userID: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("body")
    val body: String? = null
)

fun PostDto.toPost(): Post {
    return Post(
        title = title,
        body = body
    )
}
