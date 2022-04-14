package com.unken.codewars.challenges.domain.model

data class UserChallengesInfo(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeSummary>
)