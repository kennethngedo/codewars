package com.unken.codewars.challenges.domain.model


data class CodeChallenge (
    val approvedAt: String,
    val approvedBy: ApprovedBy,
    val id: String,
    val languages: List<String>,
    val name: String,
    val rank: Rank,
    val slug: String,
    val tags: List<String>,
    val totalAttempts: Int,
    val totalCompleted: Int,
    val totalStars: Int,
    val url: String,
    val voteScore: Int
)