package com.sample.todo.data.task.sqldelight

import android.content.Context
import com.sample.sqldelight.todo.data.createDatabase
import com.sample.todo.data.RepositoryBindingModule
import com.sample.todo.data.core.DataScope
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        TaskDataSourceBindingModule::class,
        RepositoryBindingModule::class
    ]
)
object TodoDatabaseModule {
    @Provides
    @JvmStatic
    @DataScope
    fun provideTodoDatabase(context: Context): TodoSqlDelightDatabase {
        val driver = AndroidSqliteDriver(TodoSqlDelightDatabase.Schema, context, "dsf")
        return createDatabase(driver)
    }

    @Provides
    @JvmStatic
    @DataScope
    fun provideTaskQueries(db: TodoSqlDelightDatabase): TaskQueries {
        return db.taskQueries
    }
}
