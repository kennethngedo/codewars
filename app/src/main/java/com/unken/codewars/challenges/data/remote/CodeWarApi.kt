package com.unken.codewars.challenges.data.remote

import com.unken.codewars.challenges.data.remote.dto.UserChallengesInfoDto
import com.unken.codewars.challenges.data.remote.dto.CodeChallengeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodeWarApi {
    @GET("/api/v1/users/some_user/code-challenges/completed?page={page}")
    suspend fun getUserInfo(@Query("page") page:Int): UserChallengesInfoDto

    @GET("/api/v1/code-challenges/{id}")
    suspend fun getCodeChallenge(@Path("id") challengeId: Int): CodeChallengeDto

    companion object {
        const val BASE_URL = "http://www.codewars.com"
    }
}