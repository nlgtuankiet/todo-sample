package com.sample.todo.data.task.room

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManager
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

    companion object {
        operator fun invoke(context: Context): DataComponent = DaggerRoomDataComponent.factory().create(context)
    }
}