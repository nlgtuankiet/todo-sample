package com.sample.todo.data.task.room.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.room.RoomDatabase
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.task.room.TaskNewDao
import com.sample.todo.data.task.room.TodoDatabase
import com.sample.todo.data.task.room.entity.TaskMiniEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt

class TaskMiniDataSource(
    private val taskDao: TaskNewDao,
    private val database: TodoDatabase,
    private val orderBy: TaskNewDao.OrderBy,
    private val orderDirection: TaskNewDao.OrderDirection
) : PageKeyedDataSource<Int, TaskMiniEntity>() {

    private var loadInitialJob: Job? = null
    private var loadAfterJob: Job? = null
    private var loadBeforeJob: Job? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TaskMiniEntity>) {
        loadInitialJob?.cancel()
        loadInitialJob = GlobalScope.launch {
            val totalPageNeeded = ceil(params.requestedLoadSize.toDouble() / defaultPageSize).roundToInt()
            val totalItemNeeded = totalPageNeeded * defaultPageSize
            database.ioTransaction {
                val size = params.requestedLoadSize
                val items = loadData(0, totalItemNeeded)
                val total = taskDao.countAllTask()
                val nextPageKey = if (size >= total) null else totalPageNeeded
                callback.onResult(items, 0, total, null, nextPageKey)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TaskMiniEntity>) {
        loadAfterJob?.cancel()
        loadAfterJob = GlobalScope.launch {
            val totalPageNeeded = ceil(params.requestedLoadSize.toDouble() / defaultPageSize).roundToInt()
            val totalItemNeeded = totalPageNeeded * defaultPageSize
            database.ioTransaction {
                val size = params.requestedLoadSize
                val items = loadData(params.key, totalItemNeeded)
                val adjacentPageKey = if (size > items.size) null else totalPageNeeded + params.key + 1
                callback.onResult(items, adjacentPageKey)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TaskMiniEntity>) {
        // later
//        1
//
//        loadInitialJob?.cancel()
//        loadInitialJob = coroutineScope.launch {
//            val totalPageNeeded = ceil(params.requestedLoadSize.toDouble() / defaultPageSize).roundToInt()
//            val totalItemNeeded = totalPageNeeded * defaultPageSize
//            val page = params.key -
//            database.ioTransaction {
//                val size = params.requestedLoadSize
//                val items = loadData(params.key, totalItemNeeded)
//                val adjacentPageKey = if (size > items.size) null else totalPageNeeded + params.key + 1
//                callback.onResult(items, adjacentPageKey)
//            }
//        }
    }

    private fun loadData(page: Int, itemPerPage: Int): List<TaskMiniEntity> {
        val offset = page * itemPerPage
        val idList = when (orderDirection) {
            is TaskNewDao.OrderDirection.Ascending -> taskDao.getIdListAsc(orderBy, itemPerPage, offset)
            is TaskNewDao.OrderDirection.Descending -> taskDao.getIdListAsc(orderBy, itemPerPage, offset)
        }
        val titleList = when (orderDirection) {
            is TaskNewDao.OrderDirection.Ascending -> taskDao.getTitleListAsc(orderBy, itemPerPage, offset)
            is TaskNewDao.OrderDirection.Descending -> taskDao.getTitleListDesc(orderBy, itemPerPage, offset)
        }
        if (idList.size != titleList.size)
            TODO()
        val completedList = when (orderDirection) {
            is TaskNewDao.OrderDirection.Ascending -> taskDao.getCompletedListAsc(orderBy, itemPerPage, offset)
            is TaskNewDao.OrderDirection.Descending -> taskDao.getCompletedListDesc(orderBy, itemPerPage, offset)
        }
        if (idList.size != completedList.size)
            TODO()
        return idList.mapIndexed { index: Int, id: String ->
            TaskMiniEntity(
                id = id,
                title = titleList[index],
                isCompleted = completedList[index]
            )
        }
    }

    private suspend fun loadDataAsync(page: Int, itemPerPage: Int): List<TaskMiniEntity> {
        TODO()
//        val a = "".also {
//
//        }
//        return database.ioTransaction {
//            loadData(page, itemPerPage)
//        }
    }

    private suspend fun <T> RoomDatabase.ioTransaction(
        action: () -> T
    ): T {
        val database = this
        return withContext(Dispatchers.IO) {
//            database.withTransaction {
                action()
//            }
        }
    }

    companion object {
        private const val defaultPageSize = 10
    }


    @DataScope
    class FactoryProvider @Inject constructor(
        private val taskDao: TaskNewDao,
        private val database: TodoDatabase
    ) {
        fun create(orderBy: TaskNewDao.OrderBy, orderDirection: TaskNewDao.OrderDirection): DataSource.Factory<Int, TaskMiniEntity> {
            return Factory(
                taskDao = taskDao,
                database = database
            ).setup(
                orderBy = orderBy,
                orderDirection = orderDirection
            )
        }
    }

    class Factory(
        private val taskDao: TaskNewDao,
        private val database: TodoDatabase
    ) : DataSource.Factory<Int, TaskMiniEntity>() {
        private lateinit var orderBy: TaskNewDao.OrderBy
        private lateinit var orderDirection: TaskNewDao.OrderDirection

        fun setup(orderBy: TaskNewDao.OrderBy, orderDirection: TaskNewDao.OrderDirection): Factory {
            this.orderBy = orderBy
            this.orderDirection = orderDirection
            return this
        }

        override fun create(): DataSource<Int, TaskMiniEntity> {
            return TaskMiniDataSource(
                taskDao = taskDao,
                database = database,
                orderBy = orderBy,
                orderDirection = orderDirection
            )
        }
    }

}