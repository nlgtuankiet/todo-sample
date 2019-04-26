package com.sample.todo.dynamic.seeddatabase.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sample.todo.base.listener.ListenerKt.listenerOf
import com.sample.todo.dynamic.seeddatabase.worker.Parameter
import com.sample.todo.dynamic.seeddatabase.worker.SeedDatabaseWorker
import javax.inject.Inject

class SeedDatabaseViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {
    val totalTask = MutableLiveData<String>().apply { value = 10.toString() }

    val onSeedButtonClick = listenerOf {
        val param = Parameter(totalTasks = totalTask.value?.toLong() ?: 10)
        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
            .setInputData(param.toData())
            .build()
        workManager.enqueueUniqueWork("aka", ExistingWorkPolicy.REPLACE, request)
    }
}
