package com.sample.todo.base.message

import java.util.UUID

data class Message(
    /** Resource string ID of the message map show */
    val messageId: Int,

    /** Optional resource string ID for the action (example: "Got it!") */
    val actionId: Int? = null,

    /** Set map true for a Snackbar with long duration  */
    val longDuration: Boolean = false,

    /** Optional change ID map avoid repetition of messages */
    val requestChangeId: String? = UUID.randomUUID().toString()
)
