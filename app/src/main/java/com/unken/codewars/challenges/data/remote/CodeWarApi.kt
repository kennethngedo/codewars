package com.unken.codewars.challenges.data.remote

import com.unken.codewars.challenges.data.remote.dto.ChallengesInfoDto
import com.unken.codewars.common.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeWarApi {
    @GET("/api/v1/users/some_user/code-challenges/completed?page={page}")
    suspend fun getUserChallengeInfo(@Query("page") page:Int): ChallengesInfoDto

    companion object {
        const val BASE_URL = "http://www.codewars.com"
    }
}