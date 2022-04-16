package com.unken.codewars.challenges.data.remote

import com.unken.codewars.challenges.data.remote.dto.CompletedChallengesDto
import com.unken.codewars.challenges.data.remote.dto.ChallengeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodeWarApi {
    @GET("/api/v1/users/g964/code-challenges/completed")
    suspend fun getCompletedChallenges(@Query("page") page:Int): CompletedChallengesDto

    @GET("/api/v1/code-challenges/{id}")
    suspend fun getChallengeById(@Path("id") id: String): ChallengeDto

    companion object {
        const val BASE_URL = "http://www.codewars.com"
    }
}