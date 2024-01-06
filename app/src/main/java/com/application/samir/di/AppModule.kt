package com.application.samir.di

import com.application.samir.common.Constants
import com.application.samir.data.remote.JsonPlaceHolderApi
import com.application.samir.data.repository.PostRepositoryImpl
import com.application.samir.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJsonPlaceHolderApi(): JsonPlaceHolderApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceHolderApi::class.java)
    }
    @Provides
    @Singleton
    fun providePostRepository(api: JsonPlaceHolderApi): PostRepository {
        return PostRepositoryImpl(api)
    }
}