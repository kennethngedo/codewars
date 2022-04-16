package com.unken.codewars.challenges.domain.usecases

import com.unken.codewars.challenges.domain.repository.CodeWarRepository

class GetChallengeDetails(
    private val repository: CodeWarRepository
) {
    operator fun invoke(id: String) = repository.getChallengeById(id)
}