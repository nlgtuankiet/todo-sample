package com.sample.todo.util.extension

import androidx.annotation.NavigationRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController

fun FragmentActivity.setupMainNavGraph(
    @NavigationRes graphResId: Int,
    hostStartDestination: Map<Int, Int>
) {
    for ((navHostFragmentId, startDestId) in hostStartDestination) {
        findNavController(navHostFragmentId).apply {
            navInflater.inflate(graphResId)
                .apply { startDestination = startDestId }
                .also { graph = it }
        }
    }
}
