package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.ChallengeSummary

data class ChallengeSummaryDto(
    val completedAt: String,
    val completedLanguages: List<String>,
    val id: String,
    val name: String,
    val slug: String
) {
    fun toChallenge(): ChallengeSummary {
        return ChallengeSummary(
            completedAt = completedAt,
            completedLanguages = completedLanguages,
            id = id,
            name = name,
            slug = slug
        )
    }
}