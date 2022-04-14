package com.unken.codewars.challenges.domain.usecases

import com.unken.codewars.challenges.domain.repository.CodeWarRepository

class GetCodeWarInfo(private val repository: CodeWarRepository) {
    operator fun invoke(page: Int) = repository.getCodeWarInfo(page)
}