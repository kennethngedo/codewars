package com.unken.codewars.challenges.data.remote

import com.unken.codewars.challenges.data.remote.dto.UserChallengesInfoDto
import com.unken.codewars.challenges.data.remote.dto.ChallengeDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodeWarApi {
    @GET("/api/v1/users/some_user/code-challenges/completed")
    suspend fun getUserInfo(@Query("page") page:Int): UserChallengesInfoDto

    @GET("/api/v1/code-challenges/{slug}")
    suspend fun getChallengeDetails(@Path("slug") slug: String): ChallengeDetailsDto

    companion object {
        const val BASE_URL = "http://www.codewars.com"
    }
}