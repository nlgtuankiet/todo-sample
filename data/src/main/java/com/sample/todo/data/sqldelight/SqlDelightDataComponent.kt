package com.sample.todo.data.sqldelight

import com.sample.todo.data.DataComponent
import com.sample.todo.data.core.DataScope
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
@DataScope
abstract class SqlDelightDataComponent : DataComponent() {

    @Component.Builder
    abstract class Builder : DataComponent.Builder()
    companion object {
        fun builder(): Builder = DaggerSqlDelightDataComponent.builder()
    }
}