package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.UserChallengesInfo

data class UserChallengesInfoDto(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeSummaryDto>
    ) {
    fun toChallengesInfo(): UserChallengesInfo {
        return UserChallengesInfo(
            totalPages = totalPages,
            totalItems = totalItems,
            data = data.map { it.toChallenge() }
        )
    }
}