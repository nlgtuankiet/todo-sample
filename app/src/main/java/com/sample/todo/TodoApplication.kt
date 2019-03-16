package com.sample.todo

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.sample.todo.data.DataComponent
import com.sample.todo.data.room.RoomDataComponent
import com.sample.todo.di.AppComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.AppInitializer
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class TodoApplication : DaggerApplication() {
    @Inject
    lateinit var appInitializer: AppInitializer

    val dataComponent: DataComponent by lazy {
        RoomDataComponent.builder().seedContext(this).build()
    }
    val domainComponent: DomainComponent by lazy {
        DomainComponent.builder()
            .taskRepository(dataComponent.provideTaskRepository())
            .preferenceRepository(dataComponent.providePreferenceRepository())
            .build()
    }
    val appComponent: AppComponent by lazy {
        AppComponent.builder()
            .domainComponent(domainComponent)
            .dataComponent(dataComponent)
            .create(this) as? AppComponent ?: TODO()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appInitializer.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    protected open fun isInUnitTests() = false
}