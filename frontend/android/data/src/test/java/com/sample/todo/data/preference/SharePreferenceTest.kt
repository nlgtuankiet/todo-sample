package com.sample.todo.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharePreferenceTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val sharePrefs: SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val listener: SharedPreferences.OnSharedPreferenceChangeListener = mock()

    @Before
    fun `set up`() {
        sharePrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    @After
    fun `tear down`() {
        reset(listener)
        sharePrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    @Test
    fun `write read value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }

        assertEquals(listOf("1"), values)
    }

    @Test
    fun `SharePreference won't notify when update same key with same value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { putString("a", "1") }

        assertEquals(listOf("1"), values)
    }

    @Test
    fun `SharePreference won't notify when create new key-value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { putString("b", "1") }

        assertEquals(listOf("1"), values)
    }

    @Test
    fun `SharePreference won't notify when update other key-value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { putString("b", "1") }
        sharePrefs.edit(commit = true) { putString("b", "2") }

        assertEquals(listOf("1"), values)
    }

    @Test
    fun `SharePreference notify when update new value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { putString("a", "2") }

        assertEquals(listOf("1", "2"), values)
    }

    @Test
    fun `SharePreference notify when delete old value`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { remove("a") }

        assertEquals(listOf("1", "default"), values)
    }

    @Test
    fun `SharePreference notify when update old value with null`() {
        val values = mutableListOf<String?>()
        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
            values.add(sharePrefs.getString("a", "default"))
        }

        sharePrefs.edit(commit = true) { putString("a", "1") }
        sharePrefs.edit(commit = true) { putString("a", null) }

        assertEquals(listOf("1", "default"), values)
    }

    @Test
    fun `SharePreference notify when update 2 null value`() {
//        val values = mutableListOf<String?>()
//        `when`(listener.onSharedPreferenceChanged(sharePrefs, "a")).then {
//            values.add(sharePrefs.getString("a", "default"))
//        }
//
//        sharePrefs.edit(commit = true) { putString("a", null) }
//        sharePrefs.edit(commit = true) { putString("a", null) }
//
//        assertEquals(listOf("default", "default"), values)
    }
}
