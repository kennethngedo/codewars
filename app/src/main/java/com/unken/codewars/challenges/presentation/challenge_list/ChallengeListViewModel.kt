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

    private val _page = mutableStateOf(1)
    val page: State<Int> = _page

    private val _state = mutableStateOf(ChallengesListState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var fetchChallengesJob: Job? = null

    private fun fetchChallenges(pageNumber: Int = 1)  {
        _page.value = pageNumber
        fetchChallengesJob?.cancel()
        fetchChallengesJob = viewModelScope.launch(Dispatchers.IO) {
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

    fun nextPage() {
        val lastPage = _state.value.completedChallenges?.totalPages ?: 1
        if (_page.value < lastPage) {
            val destinationPage = _page.value + 1
            fetchChallenges(destinationPage)
        }
    }

    fun previousPage() {
        if(_page.value > 1) {
            val destinationPage = _page.value - 1
            fetchChallenges(destinationPage)
        }
    }

    fun gotoPage(pageNumber: Int) {
        val lastPage = _state.value.completedChallenges?.totalPages ?: 1
        if (pageNumber in 1..lastPage) {
            fetchChallenges(pageNumber)
        }
    }
}