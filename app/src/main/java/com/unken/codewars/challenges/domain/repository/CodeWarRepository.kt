package com.unken.codewars.challenges.domain.repository

import com.unken.codewars.challenges.domain.model.ChallengesInfo
import com.unken.codewars.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CodeWarRepository {
    fun getCodeWarInfo(page: Int) : Flow<Resource<ChallengesInfo>>
}