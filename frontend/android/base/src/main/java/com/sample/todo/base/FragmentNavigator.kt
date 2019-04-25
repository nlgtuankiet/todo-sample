package com.sample.todo.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class FragmentNavigator(holder: Holder<out Fragment>) {
    val fragment: Fragment by lazy { holder.instance }
    val activity: FragmentActivity by lazy { fragment.requireActivity() }

    fun up() {
        fragment.findNavController().navigateUp()
    }

    fun to(navDirections: NavDirections) {
        fragment.findNavController().navigate(navDirections)
    }
}
