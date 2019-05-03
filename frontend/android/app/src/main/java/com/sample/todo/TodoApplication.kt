package com.sample.todo

// import androidx.multidex.MultiDex
// import androidx.multidex.MultiDexApplication
import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.firebase.FirebaseApp
//import com.jakewharton.threetenabp.AndroidThreeTen
import com.sample.todo.data.DataComponent
import com.sample.todo.di.android.AndroidComponent
import com.sample.todo.di.app.AppComponent
import com.sample.todo.di.application.ApplicationComponent
import com.sample.todo.initializer.AppInitializer
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class TodoApplication : DaggerApplication() {
    @Inject
    lateinit var appInitializer: AppInitializer

    lateinit var androidComponent: AndroidComponent
    lateinit var applicationComponent: ApplicationComponent
    lateinit var dataComponent: DataComponent
    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        androidComponent = AndroidComponent(this)
        applicationComponent = ApplicationComponent(androidComponent)
        dataComponent = applicationComponent.provideGetDataComponent()()
        appComponent = AppComponent(
            androidComponent = androidComponent,
            dataComponent = dataComponent
        )
        super.onCreate()
        appInitializer.initialize(this)
//        AndroidThreeTen.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
        MultiDex.install(this)
    }

    protected open fun isInUnitTests() = false
}
