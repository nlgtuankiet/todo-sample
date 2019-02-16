package com.sample.todo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.postDelayed
import com.sample.todo.R
/**
 * A custom snackbar implementation allowing more control over placement and entry/exit animations.
 */
class FadingSnackbar(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val message: TextView
    private val action: Button

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.fading_snackbar, this, true)
        message = view.findViewById(R.id.snackbar_text)
        action = view.findViewById(R.id.snackbar_action)
    }

    fun dismiss() {
        if (visibility == VISIBLE && alpha == 1f) {
            animate()
                .alpha(0f)
                .withEndAction { visibility = GONE }
                .duration = EXIT_DURATION
        }
    }

    fun show(
        @StringRes messageId: Int = 0,
        messageText: CharSequence? = null,
        @StringRes actionId: Int? = null,
        longDuration: Boolean = true,
        actionClick: () -> Unit = { dismiss() },
        dismissListener: () -> Unit = { }
    ) {
        message.text = messageText ?: context.getString(messageId)
        if (actionId != null) {
            action.run {
                visibility = VISIBLE
                text = context.getString(actionId)
                setOnClickListener {
                    actionClick()
                }
            }
        } else {
            action.visibility = GONE
        }
        alpha = 0f
        visibility = VISIBLE
        animate()
            .alpha(1f)
            .duration = ENTER_DURATION
        val showDuration = ENTER_DURATION + if (longDuration) LONG_DURATION else SHORT_DURATION
        postDelayed(showDuration) {
            dismiss()
            dismissListener()
        }
    }

    companion object {
        private const val ENTER_DURATION = 300L
        private const val EXIT_DURATION = 200L
        private const val SHORT_DURATION = 1_500L
        private const val LONG_DURATION = 2_750L
    }
}