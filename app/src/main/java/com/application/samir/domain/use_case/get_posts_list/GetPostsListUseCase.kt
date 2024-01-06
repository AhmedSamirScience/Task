package com.application.samir.domain.use_case.get_posts_list

import com.application.samir.common.network.Resource
import com.application.samir.data.remote.dto.PostDto
import com.application.samir.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostsListUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<Resource<List<PostDto>>> = flow {
        try {
            emit(Resource.Loading<List<PostDto>>())
            val posts = repository.getPostsList()

            if(posts.isNullOrEmpty())
                emit(Resource.Error<List<PostDto>>("There is no Post."))
            else
                emit(Resource.Success<List<PostDto>>(posts))
        } catch(e: HttpException) {
            emit(Resource.Error<List<PostDto>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<PostDto>>("Couldn't reach server. Check your internet connection."))
        }
    }
}