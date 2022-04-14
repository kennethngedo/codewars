package com.unken.codewars.challenges.presentation.challenge_list

import com.unken.codewars.challenges.domain.model.UserChallengesInfo

data class ChallengesInfoState (
    val userChallengesInfo: UserChallengesInfo? = null,
    val isLoading: Boolean = false
)