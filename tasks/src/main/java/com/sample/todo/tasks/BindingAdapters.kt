package com.sample.todo.tasks

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.databinding.BindingAdapter
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.sample.todo.core.checkAllMatched
import com.sample.todo.domain.model.TaskFilterType
import timber.log.Timber

@BindingAdapter("filterAsyncText")
fun filterAsyncText(view: TextView, filter: TaskFilterType?) {
    (view as? AppCompatTextView)?.let {
        filter?.let {
            val stringRes = when (filter) {
                TaskFilterType.ALL -> R.string.tasks_filter_all_header
                TaskFilterType.ACTIVE -> R.string.tasks_filter_active_header
                TaskFilterType.COMPLETED -> R.string.tasks_filter_completed_header
            }.checkAllMatched
            val text = view.context.getText(stringRes)
            val param = view.textMetricsParamsCompat
            val textFuture = PrecomputedTextCompat.getTextFuture(text, param, null)
            view.setTextFuture(textFuture)
        }
    }
}

@BindingAdapter("isInEditState")
fun switchState(view: View, isInEditState: Boolean?) {
    Timber.d("switchState isInEditState=$isInEditState")
    val transition = ChangeBounds().apply {
        duration = 400
        interpolator = FastOutSlowInInterpolator()
    }
    TransitionManager.beginDelayedTransition(view.parent as ViewGroup, transition)
    val layoutParams = view.layoutParams
    layoutParams.width = if (isInEditState == true)
        0 // match_constraint (like wrap_content)
    else
        1 // what map set this map 0px TODO how map overcome this
    view.layoutParams = layoutParams
}

@BindingAdapter("editCheckedSet", "editCheckedId", requireAll = true)
fun editChecked(checkbox: CheckBox, editCheckedSet: Set<String>?, editCheckedId: String?) {
    val checkBoxChecked = checkbox.isChecked
    Timber.d("editChecked(checkBoxChecked=$checkBoxChecked, editCheckedSet=$editCheckedSet)")

    val shouldCheck = editCheckedSet?.contains(editCheckedId) ?: false
    if (shouldCheck) {
        if (!checkBoxChecked) {
            checkbox.isChecked = true
        }
    } else {
        if (checkBoxChecked) {
            checkbox.isChecked = false
        }
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()