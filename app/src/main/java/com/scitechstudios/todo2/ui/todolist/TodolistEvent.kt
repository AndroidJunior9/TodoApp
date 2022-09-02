package com.scitechstudios.todo2.ui.todolist

import com.scitechstudios.todo2.data.ToDo


sealed class TodolistEvent{
    data class OnDeleteTodo(val todo: ToDo): TodolistEvent()
    data class OnDoneChange(val todo:ToDo,val isdone:Boolean): TodolistEvent()
    object OnUndoDelete: TodolistEvent()
    data class OnTodoClick(val todo:ToDo): TodolistEvent()
    object OnAddTodoClick: TodolistEvent()
}
