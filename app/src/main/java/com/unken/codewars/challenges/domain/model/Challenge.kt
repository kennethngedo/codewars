package com.unken.codewars.challenges.domain.model


data class Challenge (
    val id: String,
    val languages: List<String>,
    val name: String,
    val slug: String,
    val totalAttempts: Int,
    val totalCompleted: Int,
    val totalStars: Int,
    val description: String
)