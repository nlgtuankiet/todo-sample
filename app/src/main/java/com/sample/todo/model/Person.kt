package com.sample.todo.model
//
// import androidx.room.ColumnInfo
// import androidx.room.Dao
// import androidx.room.Database
// import androidx.room.Entity
// import androidx.room.Insert
// import androidx.room.PrimaryKey
// import androidx.room.Query
// import androidx.room.RoomDatabase
//
// inline class PersonId(val value: String)
//
// @Entity(tableName = "person")
// data class Person public constructor(
//    @ColumnInfo(name = "name")
//    val name: String,
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "idd")
//    val idd: PersonId
// )
//
// @Database(
//    entities = [
//        Person::class
//    ],
//    version = 1,
//    exportSchema = false
// )
// abstract class PersonDatabase : RoomDatabase() {
//    abstract fun personDao(): PersonDao
// }
//
// @Dao
// interface PersonDao {
//    @Insert
//    fun insert(entity: Person)
//
//    @Query("SELECT * FROM person WHERE idd = :id LIMIT 1")
//    fun findById(id: PersonId): Person?
//
//    @Query("SELECT * FROM person")
//    fun listAll(): List<Person>
//
// }
