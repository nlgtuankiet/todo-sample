package com.sample.todo.di

import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.sample.todo.MainActivityModule
import com.sample.todo.TodoApplication
import com.sample.todo.base.di.ApplicationScope
import com.sample.todo.base.message.MessageManagerBindingModule
import com.sample.todo.data.DataComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.InitializerBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [
        MainActivityModule::class,
        AndroidSupportInjectionModule::class,
        AndroidModule::class,
        MessageManagerBindingModule::class,
        WorkModule::class,
        InitializerBindingModule::class
    ],
    dependencies = [
        DataComponent::class,
        DomainComponent::class
    ]
)
interface AppComponent : AndroidInjector<TodoApplication> {
    fun provideNotificationManager(): NotificationManagerCompat
    fun provideSharePreference(): SharedPreferences
    fun provideWorkManager(): WorkManager
    fun provideSplitInstallManager(): SplitInstallManager

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: TodoApplication,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponent
    }

    companion object {
        operator fun invoke(
            application: TodoApplication,
            dataComponent: DataComponent,
            domainComponent: DomainComponent
        ): AppComponent = DaggerAppComponent.factory().create(
            application,
            dataComponent,
            domainComponent
        )
    }
}