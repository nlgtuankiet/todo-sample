package com.sample.todo.addedit

import androidx.annotation.StringRes
import com.airbnb.mvrx.MvRxState

data class AddEditState(
    @StringRes val toolbarTitle: Int,
    val isLoading: Boolean
) : MvRxState