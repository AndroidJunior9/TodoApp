package com.scitechstudios.todo2.ui.addedittodo


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scitechstudios.todo2.data.ToDo
import com.scitechstudios.todo2.data.TodoRepository
import com.scitechstudios.todo2.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var todo by mutableStateOf<ToDo?>(null)
        private set

    var title by mutableStateOf("")

    var description by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoid = savedStateHandle.get<Int>("todoId")
        if(todoid != -1) {
            viewModelScope.launch {
                repository.gettodobyid(todoid!!)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditTodoViewModel.todo = todo
                }

            }
        }
    }
    fun onEvent(event: AddEditTodoEvent){
        when(event) {
            is AddEditTodoEvent.OnTitleChange ->{
                title = event.title
            }

            is AddEditTodoEvent.OnDescriptionChange ->{
                description = event.description
            }

            is AddEditTodoEvent.OnSaveTodo ->{
                viewModelScope.launch {
                    if (title.isBlank()){
                       sendUiEvent(UiEvent.ShowSnackBar(
                           message = "The Title Cannot Be Blank"
                       ))
                        return@launch
                    }
                    repository.inserttodo(
                        ToDo(title = title,
                            description = description,
                            isdone = todo?.isdone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
    private fun sendUiEvent(event:UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}