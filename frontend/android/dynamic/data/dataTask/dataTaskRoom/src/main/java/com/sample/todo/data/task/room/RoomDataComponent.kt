package com.sample.todo.data.task.room

import com.sample.todo.data.DataComponent
import com.sample.todo.data.core.DataScope
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
@DataScope
interface RoomDataComponent : DataComponent {

    @Component.Factory
    interface Factory : DataComponent.Factory

    companion object : DataComponent.Companion(DaggerRoomDataComponent.factory())
}
