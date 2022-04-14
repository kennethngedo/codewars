package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.ChallengesInfo

data class ChallengesInfoDto(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeDto>
    ) {
    fun toChallengesInfo(): ChallengesInfo {
        return ChallengesInfo(
            totalPages = totalPages,
            totalItems = totalItems,
            data = data.map { it.toChallenge() }
        )
    }
}