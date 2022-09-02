package com.scitechstudios.todo2.ui.addedittodo

sealed class AddEditTodoEvent{
    data class OnTitleChange(val title:String):AddEditTodoEvent()
    data class OnDescriptionChange(val description:String):AddEditTodoEvent()
    object OnSaveTodo:AddEditTodoEvent()
}
