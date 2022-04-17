package com.unken.codewars.challenges.presentation.challenge_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unken.codewars.R
import com.unken.codewars.challenges.domain.usecases.GetCodeWarInfo
import com.unken.codewars.common.utils.Resource
import com.unken.codewars.common.utils.UIEvent
import com.unken.codewars.common.utils.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val getCodeWarInfo: GetCodeWarInfo
    ): ViewModel() {

    private val _page = mutableStateOf("1")
    val page: State<String> = _page

    private val _state = mutableStateOf(ChallengesListState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var fetchChallengesJob: Job? = null

    private fun fetchChallenges(pageNumber: Int)  {
        _page.value = pageNumber.toString()
        fetchChallengesJob?.cancel()
        fetchChallengesJob = viewModelScope.launch(Dispatchers.IO) {
            // Add a small delay, just in case the user is still typing
            // A new entry will call this method again, Hence the old job will be canceled
            delay(500L)

            getCodeWarInfo(pageNumber - 1).onEach {  result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            completedChallenges = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            completedChallenges = result.data
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: UIText.StringResource(R.string.something_went_wrong)
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun startPage() {
        fetchChallenges(1)
    }

    fun endPage() {
        val destinationPage  = _state.value.completedChallenges?.totalPages ?: 1
        fetchChallenges(destinationPage)
    }

    private fun pageIsValid(pageNumber: String): Boolean {
        _page.value = pageNumber
        return try {
            val pageInt = pageNumber.toInt()
            val lastPage = _state.value.completedChallenges?.totalPages ?: 1
            pageInt in 1..lastPage
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun nextPage() {
        if (!pageIsValid(_page.value)) return

        val lastPage = _state.value.completedChallenges?.totalPages ?: 1
        val currentPage = _page.value.toInt()
        if (currentPage < lastPage) {
            val destinationPage = currentPage + 1
            fetchChallenges(destinationPage)
        }
    }

    fun previousPage() {
        if (!pageIsValid(_page.value)) return

        val currentPage = _page.value.toInt()
        if(currentPage > 1) {
            val destinationPage = currentPage - 1
            fetchChallenges(destinationPage)
        }
    }

    fun gotoPage(pageNumber: String) {
        if (!pageIsValid(pageNumber)) return

        val pageInt = pageNumber.toInt()
        fetchChallenges(pageInt)
    }
}