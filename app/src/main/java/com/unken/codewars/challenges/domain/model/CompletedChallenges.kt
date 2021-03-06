package com.unken.codewars.challenges.domain.model

data class CompletedChallenges(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeSummary>  = arrayListOf()
)