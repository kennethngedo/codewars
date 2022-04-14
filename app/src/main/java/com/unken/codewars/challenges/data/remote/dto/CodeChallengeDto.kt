package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.CodeChallenge

data class CodeChallengeDto(
    val approvedAt: String,
    val approvedBy: ApprovedByDto,
    val category: String,
    val createdBy: CreatedByDto,
    val description: String,
    val id: String,
    val languages: List<String>,
    val name: String,
    val publishedAt: String,
    val rank: RankDto,
    val slug: String,
    val tags: List<String>,
    val totalAttempts: Int,
    val totalCompleted: Int,
    val totalStars: Int,
    val url: String,
    val voteScore: Int
) {
    fun toCodeChallenge() = CodeChallenge (
        id = id,
        approvedAt = approvedAt,
        approvedBy = approvedBy.toApprovedBy(),
        languages = languages,
        name = name,
        rank = rank.toRank(),
        slug = slug,
        tags = tags,
        totalAttempts = totalAttempts,
        totalCompleted = totalCompleted,
        totalStars = totalStars,
        url = url,
        voteScore = voteScore
    )
}