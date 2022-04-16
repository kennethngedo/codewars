package com.unken.codewars.challenges.presentation.challenge_details

import com.unken.codewars.challenges.domain.model.Challenge

data class ChallengeDetailsState (
    val challenge: Challenge? = null,
    val isLoading: Boolean = false
)