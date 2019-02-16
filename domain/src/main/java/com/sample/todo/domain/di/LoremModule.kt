package com.sample.todo.domain.di

import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import dagger.Module
import dagger.Provides

@Module
object LoremModule {
    @Provides
    @JvmStatic
    fun provideLorem(): Lorem {
        return LoremIpsum.getInstance()
    }
}