package com.sample.todo.work.seeddatabase.service

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeSeedControllerService(): SeedControllerService
}