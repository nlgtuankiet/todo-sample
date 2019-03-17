package com.sample.todo.ui

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sample.todo.util.FabData
import com.sample.todo.util.ToolbarData
import timber.log.Timber

@BindingAdapter("goneUnless")
fun goneUnless(view: View, condition: Boolean) {
    view.visibility = if (condition) VISIBLE else GONE
}

data class TextViewBindParams(val textView: AppCompatTextView, val textValue: String)

fun checkTextViewBindParams(textView: TextView, text: String?): TextViewBindParams {
    if (textView !is AppCompatTextView)
        throw IllegalArgumentException("${textView.javaClass.name} is not ${AppCompatTextView::class.java.simpleName}")
    return TextViewBindParams(
        textView = textView,
        textValue = text ?: ""
    )
}

@BindingAdapter("stringRes")
fun stringRes(view: TextView, stringRes: Int?) {
    val text = if (stringRes != null && stringRes != 0) {
        view.context.getText(stringRes) ?: ""
    } else {
        ""
    }
    view.text = text
}

@BindingAdapter("subTitleStringRes")
fun subTitleStringRes(view: Toolbar, stringRes: Int?) {
    val text = if (stringRes != null && stringRes != 0) {
        view.context.getText(stringRes)
    } else {
        ""
    }
    view.subtitle = text?.toString() ?: ""
}

@BindingAdapter("asyncText", requireAll = false)
fun asyncText(view: TextView, text: String?) {
    val (textView, textValue) = checkTextViewBindParams(view, text)
    val param = textView.textMetricsParamsCompat
    val textFuture = PrecomputedTextCompat.getTextFuture(textValue, param, null)
    textView.setTextFuture(textFuture)
}

@BindingAdapter("asyncHtmlText", requireAll = false)
fun asyncHtmlText(view: TextView, htmlText: String?) {
    val (textView, textValue) = checkTextViewBindParams(view, htmlText)
    val param = textView.textMetricsParamsCompat
    val textFuture = PrecomputedTextCompat.getTextFuture(
        HtmlCompat.fromHtml(textValue, HtmlCompat.FROM_HTML_MODE_COMPACT),
        param,
        null
    )
    textView.setTextFuture(textFuture)
}

// TODO optimize this using view tag
@BindingAdapter("data")
fun data(toolbar: Toolbar, data: ToolbarData?) {
    Timber.d("data(toolbar=$toolbar, data=$data)")
    if (data == null) return
    data.apply {
        if (navigationIcon != null) {
            toolbar.setNavigationIcon(navigationIcon)
        } else {
            toolbar.navigationIcon = null
        }

        navigationClickHandler?.let { navigationClickHandler ->
            toolbar.setNavigationOnClickListener {
                navigationClickHandler()
            }
        }
        toolbar.menu?.clear()
        if (menu != null) {
            toolbar.inflateMenu(menu)
        }

        menuItemClickHandler?.let { menuClickHandler ->
            toolbar.setOnMenuItemClickListener {
                menuClickHandler(it.itemId)
            }
        }
    }
}

@BindingAdapter("data")
fun data(fab: FloatingActionButton, data: FabData?) {
    if (data == null) return
    data.apply {
        val drawable = AppCompatResources.getDrawable(fab.context, icon)
        fab.setImageDrawable(drawable)

        fab.setOnClickListener {
            onClickHandler?.invoke()
        }
    }
}