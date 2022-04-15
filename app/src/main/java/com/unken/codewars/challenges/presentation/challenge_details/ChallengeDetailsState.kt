package com.unken.codewars.challenges.presentation.challenge_details

import com.unken.codewars.challenges.domain.model.ChallengeDetails

data class ChallengeDetailsState (
    val challengeDetails: ChallengeDetails? = null,
    val isLoading: Boolean = false
)