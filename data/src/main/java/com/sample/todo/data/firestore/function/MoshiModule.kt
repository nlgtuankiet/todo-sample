package com.sample.todo.data.firestore.function

import com.sample.todo.data.core.DataScope
import com.sample.todo.data.firestore.function.entity.SearchResponce
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
object MoshiModule {
    @Provides
    @JvmStatic
    @DataScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
    @Provides
    @JvmStatic
    @DataScope
    fun provideSearchResponceAdapter(moshi: Moshi): JsonAdapter<SearchResponce> {
        return moshi.adapter(SearchResponce::class.java)
    }
    @Provides
    @JvmStatic
    @DataScope
    fun provideAnyAdapter(moshi: Moshi): JsonAdapter<Any> {
        return moshi.adapter(Any::class.java)
    }
}