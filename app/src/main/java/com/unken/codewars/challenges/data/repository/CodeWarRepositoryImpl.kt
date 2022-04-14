package com.unken.codewars.challenges.data.repository

import com.unken.codewars.challenges.data.remote.CodeWarApi
import com.unken.codewars.challenges.domain.model.UserChallengesInfo
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CodeWarRepositoryImpl(private val api: CodeWarApi) : CodeWarRepository {
    override fun getCodeWarInfo(page: Int): Flow<Resource<UserChallengesInfo>> = flow {
        emit(Resource.Loading())
        try {
            val remoteResponse = api.getUserInfo(page)
            emit(Resource.Success(remoteResponse.toChallengesInfo()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection."))
        }
    }


}