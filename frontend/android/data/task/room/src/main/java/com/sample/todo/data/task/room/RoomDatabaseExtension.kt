package androidx.room

import android.annotation.SuppressLint
import timber.log.Timber

private const val NULL = 1
private const val LONG = 2
private const val DOUBLE = 3
private const val STRING = 4
private const val BLOB = 5
private const val NULL_QUERY = "NULL"

const val DB_LOG_TAG = "RoomDatabase"

val RoomSQLiteQuery.longBindings: List<Long>
    get() = this.mLongBindings.toList()

val RoomSQLiteQuery.doubleBindings: List<Double>
    get() = this.mDoubleBindings.toList()

val RoomSQLiteQuery.stringBindings: List<String>
    get() = this.mStringBindings.toList()

val RoomSQLiteQuery.blobBindings: List<ByteArray>
    get() = this.mBlobBindings.toList()

val RoomSQLiteQuery.bindingTypes: List<Int>
    get() = javaClass.getDeclaredField("mBindingTypes").let { field ->
        field.isAccessible = true
        return@let (field.get(this) as IntArray).toList()
    }

@SuppressLint("RestrictedApi")
fun RoomSQLiteQuery.logQuery() {
    val argList = arrayListOf<String>()
    var i = 0
    while (i < bindingTypes.size) {
        val bindingType = bindingTypes[i]
        when (bindingType) {
            NULL -> argList.add(NULL_QUERY)
            LONG -> argList.add(longBindings[i].toString())
            DOUBLE -> argList.add(doubleBindings[i].toString())
            STRING -> argList.add(stringBindings[i])
        }
        i++
    }
    val query = String.format(sql.replace("?", "%s"), *argList.toArray())
    Timber.tag(DB_LOG_TAG).d(query)
}

fun logQueryArg(query: String?, args: Array<out Any>?) {
    val query = if (query != null && args != null) {
        String.format(query.replace("?", "%s"), *args)
    } else null
    Timber.tag(DB_LOG_TAG).d(query)
}
