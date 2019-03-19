package com.sample.todo.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.sample.todo.data.core.DataScope
import dagger.Module
import dagger.Provides

@Module
object FirestoreModule {
    @Provides
    @DataScope
    @JvmStatic
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}