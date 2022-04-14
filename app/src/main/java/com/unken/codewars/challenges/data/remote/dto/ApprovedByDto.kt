package com.unken.codewars.challenges.data.remote.dto

import com.unken.codewars.challenges.domain.model.ApprovedBy

data class ApprovedByDto(
    val url: String,
    val username: String
) {
    fun toApprovedBy() = ApprovedBy(
        url = url,
        username = username
    )
}