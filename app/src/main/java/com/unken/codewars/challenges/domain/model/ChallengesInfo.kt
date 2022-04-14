package com.unken.codewars.challenges.domain.model

data class ChallengesInfo(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<Challenge>
)