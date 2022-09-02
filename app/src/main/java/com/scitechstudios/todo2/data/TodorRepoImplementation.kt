package com.scitechstudios.todo2.data

import kotlinx.coroutines.flow.Flow

class TodorRepoImplementation(
    private val dao:Tododao
): TodoRepository{
    override suspend fun inserttodo(todo: ToDo) {
        dao.inserttodo(todo)
    }

    override suspend fun deletetodo(todo: ToDo) {
        dao.deletetodo(todo)
    }

    override suspend fun gettodobyid(id: Int): ToDo? {
        return dao.gettodobyid(id)
    }

    override fun gettodos(): Flow<List<ToDo>> {
        return dao.gettodos()
    }


}