package com.scitechstudios.todo2.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scitechstudios.todo2.data.ToDo
import com.scitechstudios.todo2.data.TodoRepository
import com.scitechstudios.todo2.util.Routes
import com.scitechstudios.todo2.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel(){

    val todos = repository.gettodos()
    private var recentlyDeletedTodo: ToDo? = null
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event:TodolistEvent) {
        when(event){
            is TodolistEvent.OnTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.Add_EDIT_TODO + "?todoId=${event.todo.id}"))
            }

            is TodolistEvent.OnDeleteTodo ->{
                viewModelScope.launch {
                    recentlyDeletedTodo = event.todo
                    repository.deletetodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("Deleted","Undo"))
                }
            }

            is TodolistEvent.OnAddTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.Add_EDIT_TODO))
            }

            is TodolistEvent.OnUndoDelete ->{
                recentlyDeletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.inserttodo(todo)
                    }
                }

            }

            is TodolistEvent.OnDoneChange ->{
                viewModelScope.launch {
                    repository.inserttodo(
                        event.todo.copy(
                            isdone = event.isdone
                        )
                    )
                }
            }
        }
    }
    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}