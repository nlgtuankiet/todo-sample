package com.sample.todo

// import androidx.multidex.MultiDex
// import androidx.multidex.MultiDexApplication
import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.play.core.splitcompat.SplitCompat
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sample.todo.data.DataComponent
import com.sample.todo.di.AndroidComponent
import com.sample.todo.di.AppComponent
import com.sample.todo.di.application.ApplicationComponent
import com.sample.todo.domain.di.DomainComponent
import com.sample.todo.initializer.AppInitializer
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class TodoApplication : DaggerApplication() {
    @Inject
    lateinit var appInitializer: AppInitializer

    val applicationComponent: ApplicationComponent by lazy { ApplicationComponent(this) }
    val dataComponent: DataComponent by lazy { applicationComponent.provideGetDataComponent()(this) }
    val androidComponent: AndroidComponent by lazy { AndroidComponent(this) }
    val domainComponent: DomainComponent by lazy {
        DomainComponent(
            taskRepository = dataComponent.provideTaskRepository(),
            preferenceRepository = dataComponent.providePreferenceRepository()
        )
    }
    val appComponent: AppComponent by lazy {
        AppComponent(
            androidComponent = androidComponent,
            domainComponent = domainComponent,
            dataComponent = dataComponent
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
        MultiDex.install(this)
    }

    protected open fun isInUnitTests() = false
}
