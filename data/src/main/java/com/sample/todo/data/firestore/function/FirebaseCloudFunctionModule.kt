package com.sample.todo.data.firestore.function

import com.google.firebase.functions.FirebaseFunctions
import com.sample.todo.data.core.DataScope
import dagger.Module
import dagger.Provides

@Module
object FirebaseCloudFunctionModule {
    @Provides
    @JvmStatic
    @DataScope
    fun provideFirebaseFunction(): FirebaseFunctions {
        return FirebaseFunctions.getInstance()
    }
}