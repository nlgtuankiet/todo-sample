package com.sample.todo.data.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.sample.todo.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

inline fun <V, reified O> Map<String, V>?.getOrThrow(key: String): O {
    if (this == null) throw IllegalArgumentException("Map is null")
    if (!containsKey(key)) throw RuntimeException("$key is not available")
    val value = get(key)
    return value as? O
        ?: throw RuntimeException("""value: "$value" cannot assign to ${O::class.java.name}""")
}

private val fireStoreExecutor = Executors.newSingleThreadExecutor()
private val firestoreDispatcher: ExecutorCoroutineDispatcher = fireStoreExecutor.asCoroutineDispatcher()

val Dispatchers.FireStore: ExecutorCoroutineDispatcher
    get() = firestoreDispatcher

fun Task.toDocumentData(): HashMap<String, Any?> {
    return HashMap<String, Any?>().apply {
        put("title", title)
        put("description", description)
        put("isCompleted", isCompleted)
    }
}

suspend fun DocumentReference.getDocumentSnapshot(): DocumentSnapshot =
    withContext(Dispatchers.FireStore) {
        suspendCoroutine<DocumentSnapshot> { continuation ->
            get().apply {
                addOnFailureListener { continuation.resumeWithException(it) }
                addOnSuccessListener { continuation.resume(it) }
            }
        }
    }