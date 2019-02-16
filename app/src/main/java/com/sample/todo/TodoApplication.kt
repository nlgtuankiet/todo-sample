package com.sample.todo

import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.facebook.stetho.Stetho
import com.sample.todo.core.CrashlyticsTree
import com.sample.todo.data.DataComponent
import com.sample.todo.data.room.RoomDataComponent
import com.sample.todo.di.AppComponent
import com.sample.todo.domain.di.DomainComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

open class TodoApplication : DaggerApplication() {
    @Inject
    lateinit var workerFactory: WorkerFactory

    val dataComponent: DataComponent by lazy {
        RoomDataComponent.builder().seedContext(this).build()
    }
    val domainComponent: DomainComponent by lazy {
        DomainComponent.builder()
            .taskRepository(dataComponent.provideTaskRepository())
            .preferenceRepository(dataComponent.providePreferenceRepository())
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return AppComponent.builder()
            .domainComponent(domainComponent)
            .dataComponent(dataComponent)
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (!isInUnitTests()) {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
                Stetho.initializeWithDefaults(this)

//            LeakCanary.isInAnalyzerProcess(this)
//            LeakCanary.install(this)
            } else {
                Timber.plant(CrashlyticsTree())
            }

            WorkManager.initialize(
                this,
                Configuration.Builder().setWorkerFactory(workerFactory).build()
            )
        }
    }

    protected open fun isInUnitTests() = false
}