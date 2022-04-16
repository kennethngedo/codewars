package com.unken.codewars.challenges.presentation

import com.unken.codewars.challenges.data.repository.FakeCodeWarRepositoryCaseImpl
import com.unken.codewars.challenges.domain.usecases.GetChallengeDetails
import com.unken.codewars.challenges.presentation.challenge_details.ChallengeDetailsViewModel
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChallengeDetailsViewModelTest  {

    private lateinit var viewModel: ChallengeDetailsViewModel

    @Before
    fun setUp() {
        viewModel = ChallengeDetailsViewModel(
            GetChallengeDetails(FakeCodeWarRepositoryCaseImpl())
        )
    }

    @Test
    fun `getChallengeById(String) retrieves challenge data, when id is valid`() = runBlocking {
        viewModel.getChallengeById("page_two_item_1")

        delay(1000L)

        assert(viewModel.state.value.challenge != null)
    }

    @Test
    fun `getChallengeById(String) does NOT retrieves challenge data, when id is invalid`() = runBlocking {
        viewModel.getChallengeById("some_invalid_challenge_id")

        delay(1000L)

        assert(viewModel.state.value.challenge == null)
    }
}