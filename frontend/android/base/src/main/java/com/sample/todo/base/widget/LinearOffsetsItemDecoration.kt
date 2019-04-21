package com.sample.todo.base.widget

import android.content.res.Resources
import android.graphics.Rect
import android.util.SparseArray
import android.view.View

import androidx.annotation.IntDef
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlin.annotation.Retention

/**
 * This class can only be used in the RecyclerView which use a LinearLayoutManager or
 * its subclass.
 */
// TODO improve
class LinearOffsetsItemDecoration(
    @param:Orientation @field:Orientation
    val orientation: Int = LinearLayoutManager.VERTICAL,
    val itemOffsets: Int = 8,
    val isOffsetEdge: Boolean = true,
    val isOffsetLast: Boolean = true
) : RecyclerView.ItemDecoration() {

    private val mTypeOffsetsFactories = SparseArray<OffsetsCreator>()

    @IntDef(LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class Orientation

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.right = getDividerOffsets(parent, view)
        } else {
            outRect.bottom = getDividerOffsets(parent, view)
        }

        if (isOffsetEdge) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = if (adapterPosition == 0) outRect.right else 0
                outRect.top = outRect.right
                outRect.bottom = outRect.right
            } else {
                outRect.top = if (adapterPosition == 0) outRect.bottom else 0
                outRect.left = outRect.bottom
                outRect.right = outRect.bottom
            }
        }

        if (adapterPosition == parent.adapter!!.itemCount - 1 && !isOffsetLast) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.right = 0
            } else {
                outRect.bottom = 0
            }
        }
    }

    private fun getDividerOffsets(parent: RecyclerView, view: View): Int {
        if (mTypeOffsetsFactories.size() == 0) {
            return (itemOffsets * Resources.getSystem().displayMetrics.density).toInt()
        }

        val adapterPosition = parent.getChildAdapterPosition(view)
        val itemType = parent.adapter!!.getItemViewType(adapterPosition)
        val offsetsCreator = mTypeOffsetsFactories.get(itemType)

        return offsetsCreator?.create(parent, adapterPosition) ?: 0
    }

    fun registerTypeOffset(itemType: Int, offsetsCreator: OffsetsCreator) {
        mTypeOffsetsFactories.put(itemType, offsetsCreator)
    }

    interface OffsetsCreator {
        fun create(parent: RecyclerView, adapterPosition: Int): Int
    }
}
