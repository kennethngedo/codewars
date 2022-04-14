package com.unken.codewars.common.utils

sealed class UIEvent {
    data class ShowSnackbar(val message: String): UIEvent()
}
