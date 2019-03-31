package com.sample.todo.data.task.firestore

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.sample.todo.data.task.firestore.mapper.TaskSnapshotMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDataSourceImplTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private var isFirebaseInited = false

    @Before
    fun initFirebase() = runBlocking {
        println("initFirebase")
        if (!isFirebaseInited) {
            FirebaseApp.initializeApp(context)
            println("delay 5 secs")
            delay(5000)
            isFirebaseInited = true
        }
    }

    @Test
    fun getTaskSuccess() = runBlocking {
        println("getTaskSuccess")
        val dataSource: TaskDataSourceImpl = TaskDataSourceImpl(
                firestore = FirebaseFirestore.getInstance(),
                taskMapper = TaskSnapshotMapper()
            )

        val a = 0
        val b = a + 1
        println(dataSource.findTaskById("fMCugQu0cu0YWtNnzmjY"))
    }
}