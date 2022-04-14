package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.Challenge

data class ChallengeDto(
    val completedAt: String,
    val completedLanguages: List<String>,
    val id: String,
    val name: String,
    val slug: String
) {
    fun toChallenge(): Challenge {
        return Challenge(
            completedAt = completedAt,
            completedLanguages = completedLanguages,
            id = id,
            name = name,
            slug = slug
        )
    }
}