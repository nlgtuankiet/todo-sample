package com.sample.todo.main.about

import com.sample.todo.base.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(subcomponents = [AboutSubcomponent::class])
interface AboutModule
