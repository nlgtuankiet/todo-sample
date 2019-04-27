package com.sample.todo.dynamic.dataimplementation.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import com.sample.todo.domain.entity.DataImplementation
import com.sample.todo.domain.usecase.SetDataImplementation
import com.sample.todo.dynamic.dataimplementation.R
import javax.inject.Inject

class DataImplementationActivity : AppCompatActivity() {

    @Inject
    lateinit var setDataImplementation: SetDataImplementation

    override fun onCreate(savedInstanceState: Bundle?) {
        DataImplementationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dataimplementation_activity)
        findViewById<Button>(R.id.room_button).setOnClickListener {
            setDataImplementation(DataImplementation.ROOM)
            ProcessPhoenix.triggerRebirth(this)
        }
        findViewById<Button>(R.id.firestore_button).setOnClickListener {
            setDataImplementation(DataImplementation.FIRESTORE)
            ProcessPhoenix.triggerRebirth(this)
        }
        findViewById<Button>(R.id.sqldelight_button).setOnClickListener {
            setDataImplementation(DataImplementation.SQLDELIGHT)
            ProcessPhoenix.triggerRebirth(this)
        }
    }
}
