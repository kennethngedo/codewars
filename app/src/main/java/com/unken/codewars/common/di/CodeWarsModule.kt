package com.unken.codewars.common.di

import com.unken.codewars.challenges.data.remote.CodeWarApi
import com.unken.codewars.challenges.data.repository.CodeWarRepositoryImpl
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.challenges.domain.usecases.GetChallengeDetails
import com.unken.codewars.challenges.domain.usecases.GetCodeWarInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object CodeWarsModule {

    @Provides
    fun providesCodeWarApi() : CodeWarApi {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val builder = Retrofit.Builder()
            .baseUrl(CodeWarApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create(CodeWarApi::class.java)
    }

    @Provides
    fun providesCodeWarRepository(api: CodeWarApi): CodeWarRepository {
        return CodeWarRepositoryImpl(api)
    }

    @Provides
    fun providesGetCodeWarInfoUseCase(repository: CodeWarRepository) : GetCodeWarInfo {
        return GetCodeWarInfo(repository)
    }

    @Provides
    fun providesGetChallengeDetailsUseCase(repository: CodeWarRepository) : GetChallengeDetails {
        return GetChallengeDetails(repository)
    }


}