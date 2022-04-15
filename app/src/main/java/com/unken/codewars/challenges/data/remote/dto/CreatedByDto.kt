package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.CreatedBy

data class CreatedByDto(
    val url: String,
    val username: String
) {
    fun toCreatedBy() = CreatedBy(
        username = username,
        url = url
    )
}