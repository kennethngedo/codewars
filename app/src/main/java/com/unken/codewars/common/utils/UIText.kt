package com.unken.codewars.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(val string: String) : UIText()
    class StringResource(@StringRes val id: Int, vararg val args: Any): UIText()

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> string
            is StringResource -> stringResource(id, args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> string
            is StringResource -> context.getString(id, args)
        }
    }
}