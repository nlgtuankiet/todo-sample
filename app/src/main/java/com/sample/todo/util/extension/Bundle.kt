package com.sample.todo.util.extension

// inline fun <reified T> Bundle.getOrThrow(key: String, allowNull: Boolean = false): T {
//    if (!containsKey(key))
//        throw IllegalArgumentException("""Required argument "$key" is missing""")
//    val value: Any? = when (T::class.java) {
//        Int::javaClass -> getInt(key)
//        String::javaClass -> getString(key)
//        Boolean::javaClass -> getBoolean(key)
//        Bundle::javaClass -> getBundle(key)
//        else -> throw IllegalArgumentException("Un-supported type: ${T::class.java}")
//    }
//    if (value == null && !allowNull)
//        throw IllegalArgumentException("""Require "$key" as non-null but was a null value.""")
//    return value as T
// }