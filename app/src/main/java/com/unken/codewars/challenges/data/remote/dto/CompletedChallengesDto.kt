package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.CompletedChallenges

data class CompletedChallengesDto(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeSummaryDto>
    ) {
    fun toCompletedChallenges(): CompletedChallenges {
        return CompletedChallenges(
            totalPages = totalPages,
            totalItems = totalItems,
            data = data.map { it.toChallengeSummary() }
        )
    }
}