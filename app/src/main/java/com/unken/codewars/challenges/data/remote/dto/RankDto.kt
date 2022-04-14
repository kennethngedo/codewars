package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.Rank

data class RankDto(
    val color: String,
    val id: Int,
    val name: String
) {
    fun toRank() = Rank(
        color = color,
        id = id,
        name = name
    )
}