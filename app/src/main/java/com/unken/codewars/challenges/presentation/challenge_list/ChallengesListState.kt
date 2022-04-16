package com.unken.codewars.challenges.presentation.challenge_list

import com.unken.codewars.challenges.domain.model.CompletedChallenges

data class ChallengesListState (
    val completedChallenges: CompletedChallenges? = null,
    val isLoading: Boolean = false
)