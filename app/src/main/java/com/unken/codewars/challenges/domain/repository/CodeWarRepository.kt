package com.unken.codewars.challenges.domain.repository

import com.unken.codewars.challenges.domain.model.Challenge
import com.unken.codewars.challenges.domain.model.CompletedChallenges
import com.unken.codewars.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CodeWarRepository {
    fun getCompletedChallenges(pageNumber: Int) : Flow<Resource<CompletedChallenges>>
    fun getChallengeById(id: String) : Flow<Resource<Challenge>>
}