//package com.sample.todo.data.task.room.datasource
//
//import androidx.paging.DataSource
//import androidx.paging.PageKeyedDataSource
//import androidx.room.RoomDatabase
//import androidx.room.withTransaction
//import com.sample.todo.data.core.DataScope
//import com.sample.todo.data.task.room.TaskNewDao
//import com.sample.todo.data.task.room.TodoDatabase
//import com.sample.todo.data.task.room.entity.TaskMiniEntity
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//class TaskMiniDataSource(
//    private val taskDao: TaskNewDao,
//    private val database: TodoDatabase,
//    private val orderBy: TaskNewDao.OrderBy,
//    private val orderDirection: TaskNewDao.OrderDirection,
//    private val coroutineScope: CoroutineScope
//) : PageKeyedDataSource<Int, TaskMiniEntity>() {
//    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TaskMiniEntity>) {
//
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TaskMiniEntity>) {
//        val page = params.key
//        val size = params.requestedLoadSize
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TaskMiniEntity>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    private suspend fun loadData(page: Int, itemPerPage: Int): List<TaskMiniEntity> {
////        return database.launchTransaction(coroutineScope) {
////            val size = params.requestedLoadSize
////            val idList = when(orderDirection) {
////                is TaskNewDao.OrderDirection.Ascending -> taskDao.getIdListAsc(orderBy, size, 0)
////                is TaskNewDao.OrderDirection.Descending -> taskDao.getIdListAsc(orderBy, size, 0)
////            }
////            val titleList = when(orderDirection) {
////                is TaskNewDao.OrderDirection.Ascending -> taskDao.getTitleListAsc(orderBy, size, 0)
////                is TaskNewDao.OrderDirection.Descending -> taskDao.getTitleListDesc(orderBy, size, 0)
////            }
////            if (idList.size != titleList.size)
////                TODO()
////            val completedList = when(orderDirection) {
////                is TaskNewDao.OrderDirection.Ascending -> taskDao.getCompletedListAsc(orderBy, size, 0)
////                is TaskNewDao.OrderDirection.Descending -> taskDao.getCompletedListDesc(orderBy, size, 0)
////            }
////            if (idList.size != completedList.size)
////                TODO()
////            val total = taskDao.countAllTask()
////            // check
////            val result = idList.mapIndexed { index: Int, id: String ->
////                TaskMiniEntity(
////                    id = id,
////                    title = titleList[index],
////                    isCompleted = completedList[index]
////                )
////            }
////            result
////        }
////    }
//
//    private suspend fun RoomDatabase.launchTransaction(
//        action: CoroutineScope.() -> Unit
//    ) {
//        val database = this
//        return withContext(Dispatchers.IO) {
//            database.withTransaction {
//                action()
//            }
//        }
//    }
//
////    @DataScope
////    class Factory @Inject constructor(
////        private val taskNewDao: TaskNewDao,
////        private val coroutineScope: CoroutineScope
////    ) : DataSource.Factory<String, TaskMiniEntity>() {
////        override fun create(): DataSource<String, TaskMiniEntity> {
////            return TODO()
////        }
////    }
//
//}