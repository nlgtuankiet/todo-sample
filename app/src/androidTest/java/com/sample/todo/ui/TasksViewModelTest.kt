package com.sample.todo.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.sample.todo.tasks.TasksViewModel
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    fun testWithCompanion() {
        val a = TasksViewModel.initialState(mock())
        println(a)
    }
}