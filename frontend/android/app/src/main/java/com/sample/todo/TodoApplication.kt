package com.sample.todo

import android.content.Context
// import androidx.multidex.MultiDex
// import androidx.multidex.MultiDexApplication
import com.google.android.play.core.splitcompat.SplitCompat
import com.sample.todo.data.DataComponent
import com.sample.todo.data.task.room.RoomDataComponent
import com.sample.todo.di.AppComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.AppInitializer
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject
import com.jakewharton.threetenabp.AndroidThreeTen

open class TodoApplication : DaggerApplication() {
    @Inject
    lateinit var appInitializer: AppInitializer

    val dataComponent: DataComponent by lazy {
        RoomDataComponent(
            context = this)
    }
    val domainComponent: DomainComponent by lazy {
        DomainComponent(
            taskRepository = dataComponent.provideTaskRepository(),
            preferenceRepository = dataComponent.providePreferenceRepository()
        )
    }
    val appComponent: AppComponent by lazy {
        AppComponent(
            domainComponent = domainComponent,
            dataComponent = dataComponent,
            application = this
        )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appInitializer.initialize(this)
        AndroidThreeTen.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    protected open fun isInUnitTests() = false
}