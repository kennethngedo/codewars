package com.unken.codewars.challenges.domain.model

data class ChallengeSummary (
    val completedAt: String,
    val completedLanguages: List<String>,
    val id: String,
    val name: String,
    val slug: String
)