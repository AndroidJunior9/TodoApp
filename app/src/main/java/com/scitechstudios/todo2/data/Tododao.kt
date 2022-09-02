package com.scitechstudios.todo2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface Tododao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserttodo(todo: ToDo)

    @Delete
    suspend fun deletetodo(todo: ToDo)

    @Query("Select * From todo where id * :id ")
    suspend fun gettodobyid(id: Int):ToDo?

    @Query("Select * From todo")
    fun gettodos():Flow<List<ToDo>>
}