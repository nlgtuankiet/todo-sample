package com.sample.todo.data.task.sqldelight

import android.content.Context
import com.sample.todo.data.DataComponent
import com.sample.todo.data.core.DataScope
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
@DataScope
interface SqlDelightDataComponent : DataComponent {

    @Component.Factory
    interface Factory : DataComponent.Factory

    companion object {
        operator fun invoke(context: Context): DataComponent {
            return DaggerSqlDelightDataComponent.factory().create(context)
        }
    }
}