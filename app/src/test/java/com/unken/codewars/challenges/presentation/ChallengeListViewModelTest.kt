package com.unken.codewars.challenges.presentation

import com.unken.codewars.challenges.data.repository.FakeCodeWarRepositoryCaseImpl
import com.unken.codewars.challenges.domain.usecases.GetCodeWarInfo
import com.unken.codewars.challenges.presentation.challenge_list.ChallengeListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChallengeListViewModelTest {

    private lateinit var viewModel: ChallengeListViewModel

    @Before
    fun setUp() = runBlocking {
        viewModel = ChallengeListViewModel(
            GetCodeWarInfo(FakeCodeWarRepositoryCaseImpl())
        )
        viewModel.startPage()
        // Wait to retrieve challenge information -> totalPages
        delay(1000L)
    }

    @Test
    fun `startPage() requests first page of challenges list`()  {
        assertEquals(viewModel.page.value, "1")
    }

    @Test
    fun `endPage() requests last page of current user's completed challenges`() = runBlocking {
        val lastPage = viewModel.state.value.completedChallenges!!.totalPages
        viewModel.gotoPage(lastPage.toString())

        delay(1000L)

        assertEquals(viewModel.page.value, lastPage.toString())
    }

    @Test
    fun `nextPage() requests the page after the current page`() = runBlocking {
        val currentPage = viewModel.page.value.toInt()
        viewModel.nextPage()

        delay(1000L)

        assertEquals(viewModel.page.value, (currentPage + 1).toString())

    }

    @Test
    fun `nextPage() does NOT request a page, if the current page is the last page`() = runBlocking {
        viewModel.endPage()
        delay(1000L)

        val lastPage = viewModel.page.value
        viewModel.nextPage()

        delay(1000L)

        // The last page should still be the current page
        assertEquals(viewModel.page.value, lastPage)

    }

    @Test
    fun `previousPage() requests the page before the current page`() = runBlocking {
        // Set current page to the last page, so that we can request a previous page
        val lastPage = viewModel.state.value.completedChallenges!!.totalPages
        viewModel.gotoPage(lastPage.toString())

        delay(1000L)

        // Attempt to request the previous page
        viewModel.previousPage()

        delay(1000L)

        // Check that current page number is updated accordingly
        assertEquals((lastPage - 1).toString(), viewModel.page.value)
    }

    @Test
    fun `previousPage() does NOT requests a page, if the current page is the first page`() = runBlocking {
        // Attempt to request a previous page, when current page is the start page
        // should not make a request, because logically there are no previous pages
        viewModel.previousPage()

        delay(1000L)

        // Check that current page number is updated accordingly
        assertEquals("1", viewModel.page.value)
    }

    @Test
    fun `gotoPage(String) will request a page, if the page number is within range`() = runBlocking {
        // Attempt to request with a valid page number
        val validPageNumber = (viewModel.state.value.completedChallenges!!.totalPages - 1)
        viewModel.gotoPage(validPageNumber.toString())

        delay(1500L)

        // Check that current page number is updated, Hence the request was  made
        assertEquals(validPageNumber.toString(), viewModel.page.value)
    }

    @Test
    fun `gotoPage(String) will NOT request a page, if the page number is out of range`() = runBlocking {
        // Attempt to request with an out of range page number
        val invalidPageNumber = viewModel.state.value.completedChallenges!!.totalPages + 1
        viewModel.gotoPage(invalidPageNumber.toString())

        // After 500 milliseconds, check that a request is not being made
        delay(501L)

        // Check that page number did not change, Hence the request was not made
        assertEquals(false, viewModel.state.value.isLoading)

    }
}