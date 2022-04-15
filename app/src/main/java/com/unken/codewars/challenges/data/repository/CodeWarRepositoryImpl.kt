package com.unken.codewars.challenges.data.repository

import com.unken.codewars.R
import com.unken.codewars.challenges.data.remote.CodeWarApi
import com.unken.codewars.challenges.domain.model.ChallengeDetails
import com.unken.codewars.challenges.domain.model.UserChallengesInfo
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.common.utils.Resource
import com.unken.codewars.common.utils.UIText
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
            emit(Resource.Error(message = UIText.StringResource(R.string.something_went_wrong)))
        } catch (e: IOException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.no_internet_connection)))
        }
    }

    override fun getChallengeDetails(slug: String): Flow<Resource<ChallengeDetails>> = flow {
        emit((Resource.Loading()))
        try {
            val remoteResponse = api.getChallengeDetails(slug)
            emit(Resource.Success(remoteResponse.toChallengeDetails()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.something_went_wrong)))
        } catch (e: IOException) {
            emit(Resource.Error(message = UIText.StringResource(R.string.no_internet_connection)))
        }
    }


}