package com.sample.todo.data.task.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Dao
abstract class TaskNewDao {


    object Converters {
        @TypeConverter
        @JvmStatic
        fun OrderByToString(orderBy: OrderBy?): String? = orderBy?.value

        @TypeConverter
        @JvmStatic
        fun OrderDirectionToString(value: OrderDirection?): String? = value?.value
    }

    sealed class OrderBy( val value: String) {
        override fun toString(): String {
            return value
        }

        object CreateTime : OrderBy("create_time")
        object UpdateTime : OrderBy("update_time")
    }
    sealed class OrderDirection( val value: String) {
        override fun toString(): String {
            return value
        }
        object Ascending : OrderDirection("ASC")
        object Descending : OrderDirection("DESC")
    }

    @Query("SELECT id FROM task ORDER BY :orderBy DESC LIMIT :limit OFFSET :offset")
    abstract fun getIdListDesc(orderBy: OrderBy, limit: Int, offset: Int): List<String>

    @Query("SELECT title FROM task ORDER BY :orderBy DESC LIMIT :limit OFFSET :offset")
    abstract fun getTitleListDesc(orderBy: OrderBy, limit: Int, offset: Int): List<String>

    @Query("SELECT completed FROM task ORDER BY :orderBy DESC LIMIT :limit OFFSET :offset")
    abstract fun getCompletedListDesc(orderBy: OrderBy, limit: Int, offset: Int): List<Boolean>

    @Query("SELECT id FROM task ORDER BY :orderBy ASC LIMIT :limit OFFSET :offset")
    abstract fun getIdListAsc(orderBy: OrderBy, limit: Int, offset: Int): List<String>

    @Query("SELECT title FROM task ORDER BY :orderBy ASC LIMIT :limit OFFSET :offset")
    abstract fun getTitleListAsc(orderBy: OrderBy, limit: Int, offset: Int): List<String>

    @Query("SELECT completed FROM task ORDER BY :orderBy ASC LIMIT :limit OFFSET :offset")
    abstract fun getCompletedListAsc(orderBy: OrderBy, limit: Int, offset: Int): List<Boolean>

    @Query("SELECT Count(*) FROM task")
    abstract fun countAllTask(): Int
}

