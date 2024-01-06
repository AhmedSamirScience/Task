package com.application.samir.presentation.fragment.list.clickEvent

import com.application.samir.data.remote.dto.PostDto

interface PostListClickListener {
    fun onItemClicked(itemSelected: PostDto)
}