package com.sample.todo.data.task.room

import android.database.Cursor
import androidx.room.DB_LOG_TAG
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.logQuery
import androidx.room.logQueryArg
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import timber.log.Timber

abstract class LoggingRoomDatabase : RoomDatabase() {

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        Timber.tag(DB_LOG_TAG).d(sql)
        return super.compileStatement(sql)
    }

    override fun query(query: String?, args: Array<out Any>?): Cursor {
            logQueryArg(query, args)
        return super.query(query, args)
    }

    override fun query(query: SupportSQLiteQuery?): Cursor {
            (query as? RoomSQLiteQuery)?.logQuery()
//        Timber.d("query(query=(sql=${query?.sql}, argCount=${query?.argCount}))")
        return super.query(query)
    }
}