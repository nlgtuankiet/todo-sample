package com.sample.todo.data.room

import android.content.Context
import androidx.room.Room
import com.sample.todo.data.TaskRepositoryBindingModule
import com.sample.todo.data.core.DataScope
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        TaskDataSourceBindingModule::class,
        TaskRepositoryBindingModule::class
    ]
)
object TodoDatabaseModule {
    @Provides
    @JvmStatic
    @DataScope
    fun provideTodoDatabase(context: Context): TodoDatabase {
        return Room
            .databaseBuilder(context, TodoDatabase::class.java, "db")
            .build()
    }

    @Provides
    @JvmStatic
    @DataScope
    fun provideTaskDao(db: TodoDatabase): TaskDao {
        return db.taskDao()
    }
}