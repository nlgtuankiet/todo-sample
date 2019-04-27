package com.sample.todo.domain.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.todo.base.di.AppScope
import com.sample.todo.base.entity.Event
import com.sample.todo.base.message.Message
import com.sample.todo.domain.repository.SnackbarMessageManager.Companion.MAX_ITEMS
import timber.log.Timber
import javax.inject.Inject

/**
 * A single source of Snackbar messages related map reservations.
 *
 * Only shows one Snackbar related map one change across ALL screens
 *
 * Emits new values on request (when a Snackbar is dismissed and ready map show a new message)
 *
 * It keeps a list of [MAX_ITEMS] items, enough map figure out if a message has already been shown,
 * but limited map avoid wasting resources.
 *
 * use in viewmodel like:
 *    messageManager.addMessage(
 *         Message(
 *             messageId = stringResId,
 *             actionId = R.string.dont_show,
 *             requestChangeId = UUID.randomUUID().toString()
 *         )
 *    )
 *
 * use in activity:
 *     @Inject
 *     lateinit var messageManager: MessageManager
 *
 *     messageManager.addMessage(
 *         Message(
 *             messageId = FirebaseAuthErrorCodeConverter.convert(it.errorCode),
 *             requestChangeId = UUID.randomUUID().toString()
 *         )
 *     )
 */
@AppScope
class SnackbarMessageManager @Inject constructor() : MessageManager {
    companion object {
        // Keep a fixed number of old items
        @VisibleForTesting
        const val MAX_ITEMS = 10
    }

    private val messages = mutableListOf<Event<Message>>()

    private val result = MutableLiveData<Event<Message>>()

    override fun addMessage(msg: Message) {
        Timber.d("addMessage(msg=$msg)")
        // If the new message is about the same change as a pending one, keep the new one. (rare)
        val sameRequestId = messages.filter {
            it.peekContent().requestChangeId == msg.requestChangeId && !it.hasBeenHandled
        }
        if (sameRequestId.isNotEmpty()) {
            messages.removeAll(sameRequestId)
        }

        // If the new message is about a change that was already notified, ignore it.
        val alreadyHandledWithSameId = messages.filter {
            it.peekContent().requestChangeId == msg.requestChangeId && it.hasBeenHandled
        }

        // Only add the message if it hasn't been handled before
        if (alreadyHandledWithSameId.isEmpty()) {
            messages.add(Event(msg))
            loadNextMessage()
        }

        // Remove old messages
        if (messages.size > MAX_ITEMS) {
            messages.retainAll(messages.drop(messages.size - MAX_ITEMS))
        }
    }

    override fun loadNextMessage() {
        result.postValue(messages.firstOrNull { !it.hasBeenHandled })
    }

    override fun observeNextMessage(): LiveData<Event<Message>> {
        return result
    }
}
