package com.unken.codewars.challenges.di

import com.unken.codewars.challenges.data.remote.CodeWarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object CodeWarsModule {

    @Provides
    fun providesCodeWarApi() : CodeWarApi {
        val builder = Retrofit.Builder()
            .baseUrl(CodeWarApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create(CodeWarApi::class.java)
    }
}