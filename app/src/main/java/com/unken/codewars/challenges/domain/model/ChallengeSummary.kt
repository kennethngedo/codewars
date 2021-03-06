package com.unken.codewars.challenges.domain.model

import java.io.Serializable

data class ChallengeSummary (
    val completedAt: String,
    val completedLanguages: List<String>,
    val id: String,
    val name: String? = "Not Available",
    val slug: String? = "Not Available"
): Serializable