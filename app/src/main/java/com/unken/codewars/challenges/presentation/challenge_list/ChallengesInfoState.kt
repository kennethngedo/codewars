package com.unken.codewars.challenges.presentation.challenge_list

import com.unken.codewars.challenges.domain.model.ChallengesInfo

data class ChallengesInfoState (
    val challengesInfo: ChallengesInfo? = null,
    val isLoading: Boolean = false
)