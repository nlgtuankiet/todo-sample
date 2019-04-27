package com.sample.todo.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesExtensionTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val sharePrefs: SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    @Test
    fun `observableOf update value`() {
        val result = sharePrefs.observableOf("key", "default-value")
        val observer = result.test()
        sharePrefs.edit(commit = true) {
            putString("key", "a")
        }
        observer.assertValues("default-value", "a")
        observer.assertNotComplete()
    }

    @Test
    fun `observableOf stop update value when disposed`() {
        val result = sharePrefs.observableOf("key", "default-value")
        val observer = result.test()
        sharePrefs.edit(commit = true) {
            putString("key", "a")
        }
        observer.dispose()
        sharePrefs.edit(commit = true) {
            putString("key", "b")
        }

        observer.assertValues("default-value", "a")
    }
}
