package com.application.samir.domain.use_case.get_post

import com.application.samir.common.network.Resource
import com.application.samir.data.remote.dto.toPost
import com.application.samir.domain.model.Post
import com.application.samir.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(postId: String): Flow<Resource<Post>> = flow {
        try {
            emit(Resource.Loading<Post>())
            val post = repository.getPostById(postId).toPost()

            if(post.body.isNullOrBlank())
                emit(Resource.Error<Post>("body is null or blank."))
            else if(post.title.isNullOrBlank())
                emit(Resource.Error<Post>("title is null or blank."))
            else
                emit(Resource.Success<Post>(post))
        } catch(e: HttpException) {
            emit(Resource.Error<Post>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<Post>("Couldn't reach server. Check your internet connection."))
        }
    }
}