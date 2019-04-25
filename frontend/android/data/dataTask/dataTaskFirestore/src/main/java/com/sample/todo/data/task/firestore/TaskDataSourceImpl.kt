package com.sample.todo.data.task.firestore

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.functions.FirebaseFunctions
import com.sample.todo.data.TaskDataSource
import com.sample.todo.data.core.DataScope
import com.sample.todo.data.core.Mapper
import com.sample.todo.data.task.firestore.entity.TaskFieldName
import com.sample.todo.data.task.firestore.function.SearchResultDataSourcefactory
import com.sample.todo.data.task.firestore.function.entity.SearchResponce
import com.sample.todo.domain.model.SearchResult
import com.sample.todo.domain.model.SearchResultStatistics
import com.sample.todo.domain.model.Task
import com.sample.todo.domain.model.TaskMini
import com.sample.todo.domain.model.TaskStatistics
import com.squareup.moshi.JsonAdapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@DataScope
class TaskDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val documentSnapshotToTask: Mapper<DocumentSnapshot, Task>,
    private val documentSnapshotToTaskMini: Mapper<DocumentSnapshot, TaskMini>,
    private val firebaseFunctions: FirebaseFunctions,
    private val searchResponceAdapter: JsonAdapter<SearchResponce>,
    private val searchResultDataSourceFactoryFactory: SearchResultDataSourcefactory.Factory
) : TaskDataSource {
    private val userDocRef = firestore
        .collection("user")
        .document("yTKBsO7PrtkY8W5dtPyr") // mock user
    private val taskCollectionRef = userDocRef
        .collection(TaskFieldName.collection)

    override suspend fun findTaskById(taskId: String): Task? {
        return withContext(Dispatchers.IO) {
            delay(2000) // fake load
            val snapShot = taskCollectionRef.document(taskId).getDocumentSnapshot()
            documentSnapshotToTask(snapShot)
        }
    }

    override suspend fun insert(entity: Task): Long = withContext(Dispatchers.FireStore) {
        suspendCoroutine<Long> { continuation ->
            val data = entity.toDocumentData()
            taskCollectionRef.document(entity.id).set(data).apply {
                addOnSuccessListener { continuation.resume(1) }
                addOnFailureListener { continuation.resume(0) }
            }
        }
    }

    override suspend fun insertAll(entities: List<Task>): Long =
        withContext(Dispatchers.FireStore) {
            suspendCoroutine<Long> { continuation ->
                firestore.batch().apply {
                    entities.forEach { task ->
                        val ref = taskCollectionRef.document(task.id)
                        val data = task.toDocumentData()
                        set(ref, data)
                    }
                }.commit().apply {
                    addOnSuccessListener { continuation.resume(entities.size.toLong()) }
                    addOnFailureListener { continuation.resume(0) }
                }
            }
        }

    override suspend fun updateComplete(
        taskId: String,
        completed: Boolean,
        updateTime: Long
    ): Long = withContext(Dispatchers.FireStore) {
        suspendCoroutine<Long> { continuation ->
            val data = HashMap<String, Any?>().apply {
                put(TaskFieldName.isCompleted, completed)
            }
            taskCollectionRef.document(taskId).set(data, SetOptions.merge()).apply {
                addOnCompleteListener { continuation.resume(1) }
                addOnFailureListener { continuation.resume(0) }
            }
        }
    }

    override suspend fun update(task: Task): Long = withContext(Dispatchers.FireStore) {
        suspendCoroutine<Long> { continuation ->
            val data = task.toDocumentData()
            taskCollectionRef.document(task.id).set(data, SetOptions.merge()).apply {
                addOnCompleteListener { continuation.resume(1) }
                addOnFailureListener { continuation.resume(0) }
            }
        }
    }

    // TODo bug when value not update when task change
    override fun getTaskStatisticsObservable(): Observable<TaskStatistics> {
        return Observable.zip(
            getTotalShardValue("totalShard"),
            getTotalShardValue("activeShard"),
            getTotalShardValue("completedShard"),
            Function3 { total, active, completed ->
                TaskStatistics(
                    taskCount = total,
                    activeTaskCount = active,
                    completedTaskCount = completed
                )
            }
        )
    }

    private fun getTotalShardValue(shardCollectionName: String): Observable<Long> {
        val result = BehaviorSubject.create<Long>()
        userDocRef
            .collection(shardCollectionName)
            .orderBy("count")
            .addSnapshotListener(
                Dispatchers.FireStore.executor,
                EventListener<QuerySnapshot?> { snapshot, firebaseFirestoreException ->
                    if (snapshot == null || firebaseFirestoreException != null) {
                        Timber.d("snapshot is ${snapshot?.documents?.map { it.data }}, ex: ${firebaseFirestoreException?.message}")
                        result.onNext(0)
                    } else {
                        snapshot.documents.mapNotNull {
                            it.getLong("count")
                        }.sum().let { result.onNext(it) }
                    }
                })
        return result.toFlowable(BackpressureStrategy.LATEST).toObservable()
    }

    override fun tasksCountObservable(): Observable<Long> {
        return getTotalShardValue("totalShard")
    }

    private fun getConfig(pageSize: Int): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
    }

    override fun getTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        val queryFactory = QueryFactory.Builder(taskCollectionRef)
            .orderBy(TaskFieldName.createTime, Query.Direction.ASCENDING)
            .build()
        return FirestoreItemKeyedDataSource.Factory(queryFactory)
            .map(documentSnapshotToTaskMini::invoke)
            .toObservable(
                config = getConfig(pageSize)
            )
    }

    override fun getCompletedTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        val queryFactory = QueryFactory.Builder(taskCollectionRef)
            .whereEqualTo(TaskFieldName.isCompleted, true)
            .orderBy(TaskFieldName.createTime, Query.Direction.ASCENDING)
            .build()
        return FirestoreItemKeyedDataSource.Factory(queryFactory)
            .map(documentSnapshotToTaskMini::invoke)
            .toObservable(config = getConfig(pageSize))
    }

    override fun getActiveTaskMiniObservablePaged(pageSize: Int): Observable<PagedList<TaskMini>> {
        val queryFactory = QueryFactory.Builder(taskCollectionRef)
            .whereEqualTo(TaskFieldName.isCompleted, false)
            .orderBy(TaskFieldName.createTime, Query.Direction.ASCENDING)
            .build()
        return FirestoreItemKeyedDataSource.Factory(queryFactory)
            .map(documentSnapshotToTaskMini::invoke)
            .toObservable(config = getConfig(pageSize))
    }

    override fun getSearchResultObservablePaged(
        query: String,
        pageSize: Int
    ): Observable<PagedList<SearchResult>> {
        return searchResultDataSourceFactoryFactory.create(query).toObservable(
            PagedList.Config.Builder().setPageSize(pageSize).setEnablePlaceholders(false).build()
        )
    }

    override fun getSearchResultStatisticsObservable(query: String): Observable<SearchResultStatistics> {
        return Observable.just(SearchResultStatistics(0, 0))
    }

    override fun findByIdObservable(id: String): Observable<List<Task>> {
        val result: BehaviorSubject<List<Task>> = BehaviorSubject.create()
        taskCollectionRef.document(id).addSnapshotListener(
            Dispatchers.FireStore.executor,
            EventListener<DocumentSnapshot?> { snapshot, exception ->
                if (exception != null || snapshot == null) {
                    result.onNext(emptyList())
                } else {
                    runCatching { documentSnapshotToTask(snapshot) }
                        .onSuccess { result.onNext(listOf(it)) }
                        .onFailure { result.onNext(emptyList()) }
                }
            })
        return result.toFlowable(BackpressureStrategy.LATEST).toObservable()
    }

    override suspend fun deleteTask(id: String): Long = withContext(Dispatchers.FireStore) {
        suspendCoroutine<Long> { continuation ->
            taskCollectionRef.document(id).delete().apply {
                addOnCompleteListener { continuation.resume(1) }
                addOnFailureListener { continuation.resume(0) }
            }
        }
    }
}
