package com.sample.todo.model

// enum class Lang {
//    TR,
//    EN,
//    ES;
//    companion object {
//        @JvmStatic
//        @TypeConverter
//        fun toInt(langs: Set<Lang>): Int {
//            return langs.fold(0) { left, lang ->
//                left.or(1 shl lang.ordinal)
//            }
//        }
//        @JvmStatic
//        @TypeConverter
//        fun toSet(value: Int): Set<Lang> {
//            return Lang.values().filter {
//                (1 shl it.ordinal).and(value) != 0
//            }.toSet()
//        }
//    }
// }