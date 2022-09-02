package com.scitechstudios.todo2.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun inserttodo(todo: ToDo)

    suspend fun deletetodo(todo: ToDo)

    suspend fun gettodobyid(id: Int):ToDo?


    fun gettodos(): Flow<List<ToDo>>
}