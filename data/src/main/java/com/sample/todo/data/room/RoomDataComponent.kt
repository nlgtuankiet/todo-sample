package com.sample.todo.data.room

import com.sample.todo.data.DataComponent
import com.sample.todo.data.core.DataScope
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
@DataScope
abstract class RoomDataComponent : DataComponent() {

    @Component.Builder
    abstract class Builder : DataComponent.Builder()
    companion object {
        fun builder(): Builder = DaggerRoomDataComponent.builder()
    }
}