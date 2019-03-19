package com.sample.todo.data.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

class PageKey(
    private val mStartAfter: DocumentSnapshot?,
    private val mEndBefore: DocumentSnapshot?
) {
    fun getPageQuery(baseQuery: Query, size: Long): Query {
        var pageQuery = baseQuery

        if (mStartAfter != null) {
            pageQuery = pageQuery.startAfter(mStartAfter)
        }

        if (mEndBefore != null) {
            pageQuery = pageQuery.endBefore(mEndBefore)
        } else {
            pageQuery = pageQuery.limit(size)
        }

        return pageQuery
    }

    override fun toString(): String {
        val startAfter = mStartAfter?.id
        val endBefore = mEndBefore?.id
        return "PageKey{" +
            "StartAfter=" + startAfter +
            ", EndBefore=" + endBefore +
            '}'.toString()
    }
}