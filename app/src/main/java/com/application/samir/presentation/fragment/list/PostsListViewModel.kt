package com.application.samir.presentation.fragment.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.application.samir.common.base.BaseViewModel
import com.application.samir.common.network.Resource
import com.application.samir.data.remote.dto.PostDto
import com.application.samir.domain.use_case.get_posts_list.GetPostsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostsListViewModel  @Inject constructor(
    private val getPostsListUseCase: GetPostsListUseCase
) : BaseViewModel()  {

    override fun stop() {}
    override fun start() {
        getPostsList()
    }

    private val _postLiveDate: MutableStateFlow<Resource<List<PostDto>>> = MutableStateFlow(Resource.Empty())
    val postLiveDate: StateFlow<Resource<List<PostDto>>> get() = _postLiveDate
    private fun getPostsList() {
        getPostsListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("getPostsList", "Success"+ result.data)
                    _postLiveDate.value = Resource.Success(data = result.data)
                }
                is Resource.Error -> {
                    Log.e("getPostsList", "Error")
                    _postLiveDate.value = Resource.Error(message = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    Log.e("getPostsList", "Loading")
                    _postLiveDate.value = Resource.Loading()
                }
                is Resource.Empty -> {
                    _postLiveDate.value = Resource.Empty()
                }
            }
        }.launchIn(viewModelScope)
    }
}