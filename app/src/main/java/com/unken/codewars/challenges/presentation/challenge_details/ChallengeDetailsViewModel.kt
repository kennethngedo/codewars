package com.unken.codewars.challenges.presentation.challenge_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unken.codewars.R
import com.unken.codewars.challenges.domain.usecases.GetChallengeDetails
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
class ChallengeDetailsViewModel @Inject constructor(
    private val getChallengeDetails: GetChallengeDetails
): ViewModel() {
    private val _state = mutableStateOf(ChallengeDetailsState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getChallengeById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getChallengeDetails(id).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            challenge = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            challenge = result.data
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = result.message ?: UIText.StringResource(R.string.something_went_wrong)
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}