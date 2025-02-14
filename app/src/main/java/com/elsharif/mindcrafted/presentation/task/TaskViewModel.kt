package com.elsharif.mindcrafted.presentation.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.domain.repository.SessionRepository
import com.elsharif.mindcrafted.domain.repository.SubjectRepository
import com.elsharif.mindcrafted.domain.repository.TaskRepository
import com.elsharif.mindcrafted.presentation.navArgs
import com.elsharif.mindcrafted.presentation.subject.SubjectScreenNavArgs
import com.elsharif.mindcrafted.presentation.subject.SubjectState
import com.elsharif.mindcrafted.util.Priority
import com.elsharif.mindcrafted.util.SnackbarEvent
import com.elsharif.mindcrafted.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(TaskState())


    val state = combine(
        _state,
        subjectRepository.getAllSubjects(),
        ){ state,subjects ->
        state.copy(
            subject = subjects)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = TaskState()
    )

    private val _snackbarEventFlow= MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow =_snackbarEventFlow.asSharedFlow()

    init {
        fetchTask()
        fetchSubject()
    }

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.OnTitleChange -> {
                _state.update { it.copy(title = event.title) }
            }
            is TaskEvent.OnDescriptionChange -> {
                _state.update { it.copy(description = event.description) }
            }
            is TaskEvent.OnDateChange ->   {
                _state.update {
                    it.copy(
                        dueDate = event.millis
                    )
                }
            }
            is TaskEvent.OnPriorityChange ->{
                _state.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }
            TaskEvent.OnIsCompleteChange ->  {
                _state.update {
                    it.copy(isTaskComplete = !_state.value.isTaskComplete)
                }
            }
            is TaskEvent.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name,
                        subjectId=event.subject.subjectId
                    )
                }
            }
            TaskEvent.SaveTask -> saveTask()
            TaskEvent.DeleteTask -> deleteTask()
        }
    }

    private fun deleteTask(){
        viewModelScope.launch {
            try {
                val currentTaskId=state.value.currentTaskId

                if(currentTaskId!= null){
                    withContext(Dispatchers.IO){
                        taskRepository.deleteTask(taskId = currentTaskId)
                    }
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "Task deleted successfully.")
                    )
                    _snackbarEventFlow.emit(SnackbarEvent.NavigateUp)
                }else {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "No Task to delete.")
                    )
                }
            }catch (e:Exception){

                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't delete task. ${e.message}",
                        duration =  SnackbarDuration.Long
                    )
                )
            }

        }

    }

    private fun saveTask() {
        viewModelScope.launch {
            try {
                val state = _state.value
                if (state.relatedToSubject == null || state.subjectId == null) {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(
                            "Please select subject related to the task",
                            SnackbarDuration.Long
                        )
                    )
                    return@launch
                }
                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate ?: Instant.now().toEpochMilli(),
                        relatedToSubject = state.relatedToSubject,
                        priority = state.priority.value,
                        isComplete = state.isTaskComplete,
                        taskSubjectId = state.subjectId,
                        taskId = state.currentTaskId)
                 )


                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Task saved successfully"
                    )
                )
                _snackbarEventFlow.emit(SnackbarEvent.NavigateUp)

            }catch (e:Exception){
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        "Couldn't save task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )

            }
        }
    }

    private fun fetchTask(){
        viewModelScope.launch {
            navArgs.taskId?.let {id->
                taskRepository.getTaskById(id)?.let {task->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isComplete,
                            relatedToSubject = task.relatedToSubject,
                            priority = Priority.fromInt(task.priority),
                            subjectId = task.taskSubjectId,
                            currentTaskId = task.taskId
                        )
                    }
                }
            }
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            navArgs.subjectId?.let {id->
                subjectRepository.getSubjectById(id)?.let {subject->
                    _state.update {
                        it.copy(
                            subjectId = subject.subjectId,
                            relatedToSubject = subject.name
                        )
                    }
                }
            }
        }
    }

}