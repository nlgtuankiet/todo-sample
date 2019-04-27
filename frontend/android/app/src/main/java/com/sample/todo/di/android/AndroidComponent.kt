package com.sample.todo.di.android

import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.TodoApplication

interface AndroidComponent {
    fun application(): TodoApplication
    fun context(): Context
    fun notificationManagerConpat(): NotificationManagerCompat
    fun sharePreference(): SharedPreferences
    fun splitInstallManager(): SplitInstallManager
    fun workManager(): WorkManager

    companion object {
        operator fun invoke(application: TodoApplication): AndroidComponent =
            DaggerAndroidComponentImpl.factory().create(application)
    }
}
