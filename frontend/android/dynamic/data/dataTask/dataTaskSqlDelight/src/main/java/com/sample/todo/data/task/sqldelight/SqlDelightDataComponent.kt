package com.sample.todo.data.task.sqldelight

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

    companion object : DataComponent.Companion(DaggerSqlDelightDataComponent.factory())
}
