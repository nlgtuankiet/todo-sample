package com.sample.todo.util.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.ViewModelContext

val ViewModelContext.arguments: Bundle
    get() = (this as? FragmentViewModelContext)?.fragment?.arguments ?: TODO()

fun <F : Fragment> ViewModelContext.getFragment(): F {
    @Suppress("UNCHECKED_CAST")
    return ((this as? FragmentViewModelContext)?.fragment) as? F ?: TODO()
}