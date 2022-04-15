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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val getCodeWarInfo: GetCodeWarInfo
    ): ViewModel() {
    private val _page = mutableStateOf(0)
    val page: State<Int> = _page

    private val _state = mutableStateOf(ChallengesListState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    fun fetchChallenges(page: Int = 0) {
        _page.value = page
        viewModelScope.launch(Dispatchers.IO) {
            getCodeWarInfo(page).onEach {  result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            userChallengesInfo = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            userChallengesInfo = result.data
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
}