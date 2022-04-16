package com.unken.codewars.challenges.data.repository

import com.unken.codewars.R
import com.unken.codewars.challenges.data.remote.CodeWarApi
import com.unken.codewars.challenges.domain.model.Challenge
import com.unken.codewars.challenges.domain.model.CompletedChallenges
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.common.utils.Resource
import com.unken.codewars.common.utils.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CodeWarRepositoryImpl(private val api: CodeWarApi) : CodeWarRepository {
    override fun getCompletedChallenges(pageNumber: Int): Flow<Resource<CompletedChallenges>> = flow {
        emit(Resource.Loading())
        try {
            val remoteResponse = api.getCompletedChallenges(pageNumber)
            emit(Resource.Success(remoteResponse.toCompletedChallenges()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.something_went_wrong)))
        } catch (e: IOException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.no_internet_connection)))
        }
    }

    override fun getChallengeById(id: String): Flow<Resource<Challenge>> = flow {
        emit((Resource.Loading()))
        try {
            val remoteResponse = api.getChallengeById(id)
            emit(Resource.Success(remoteResponse.toChallenge()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.something_went_wrong)))
        } catch (e: IOException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.no_internet_connection)))
        }
    }


}