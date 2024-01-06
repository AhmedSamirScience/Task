package com.application.samir.presentation.fragment.main

import androidx.lifecycle.viewModelScope
import com.application.samir.common.base.BaseViewModel
import com.application.samir.common.network.Resource
import com.application.samir.domain.model.Post
import com.application.samir.domain.use_case.get_post.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
    ) : BaseViewModel() {

    override fun start() {
        getPost("1")
    }
    override fun stop() {}

    private val _postLiveDate: MutableStateFlow<Resource<Post>> = MutableStateFlow(Resource.Empty())
    val postLiveDate: StateFlow<Resource<Post>> get() = _postLiveDate
    private fun getPost(postId: String) {
        getPostUseCase(postId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                   _postLiveDate.value = Resource.Success(data = result.data)
                }
                is Resource.Error -> {
                     _postLiveDate.value = Resource.Error(message = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                     _postLiveDate.value = Resource.Loading()
                }
                is Resource.Empty -> {
                     _postLiveDate.value = Resource.Empty()
                }
            }
        }.launchIn(viewModelScope)
    }


}