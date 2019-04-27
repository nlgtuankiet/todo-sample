package com.sample.todo.domain.repository

import androidx.lifecycle.LiveData
import com.sample.todo.base.entity.Event
import com.sample.todo.base.message.Message

interface MessageManager {
    fun addMessage(msg: Message)
    fun loadNextMessage()
    fun observeNextMessage(): LiveData<Event<Message>>
}
