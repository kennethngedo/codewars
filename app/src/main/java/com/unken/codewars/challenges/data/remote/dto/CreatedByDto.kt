package com.unken.codewars.challenges.data.remote.dto

data class CreatedByDto(
    val url: String,
    val username: String
) {
    fun toCreatedBy() = CreatedBy(
        url = url,
        username = username
    )
}