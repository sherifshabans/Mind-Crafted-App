package com.elsharif.mindcrafted.presentation.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.domain.repository.SessionRepository
import com.elsharif.mindcrafted.domain.repository.SubjectRepository
import com.elsharif.mindcrafted.domain.repository.TaskRepository
import com.elsharif.mindcrafted.util.SnackbarEvent
import com.elsharif.mindcrafted.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository
):ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHours(),
        subjectRepository.getAllSubjects(),
        sessionRepository.getTotalSessionDuration()
    ){ state, subjectCount, goalHours, subjects, totalSessionDuration ->
        state.copy(
            totalSubjectCount = subjectCount,
            totalGoalStudyHours = goalHours,
            subjects=subjects,
            totalStudiedHours = totalSessionDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )


    val tasks:StateFlow<List<Task>> =taskRepository.getAllUpcomingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentSessions:StateFlow<List<Session>> =sessionRepository.getRecentFiveSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _snackbarEventFlow= MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow =_snackbarEventFlow.asSharedFlow()


    fun onEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.onSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is DashboardEvent.onGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is DashboardEvent.onSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.colors)
                }
            }
            is DashboardEvent.onDeleteSessionButtonClick ->{
                _state.update {
                    it.copy(session = event.session)
                }
            }
            DashboardEvent.SaveSubject -> saveSubject()
            DashboardEvent.DeleteSession -> {

            }
            is DashboardEvent.onTaskIsCompleteChange -> TODO()
        }
    }

    private fun saveSubject() {
          viewModelScope.launch {
      try {
              subjectRepository.upsertSubject(
                  subject = Subject(
                      name= state.value.subjectName,
                      goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                      colors = state.value.subjectCardColors.map {it.toArgb()}

                  )
              )
              _state.update {
                  it.copy(
                      subjectName = "",
                      goalStudyHours = "",
                      subjectCardColors = Subject.subjectCardColors.random()
                  )
              }
          _snackbarEventFlow.emit(
              SnackbarEvent.ShowSnackbar(
                  "Subject saved successfully"
              )
             )

      } catch (e:Exception){

          _snackbarEventFlow.emit(
              SnackbarEvent.ShowSnackbar(
                  "Couldn't save subject. ${e.message}",
                  SnackbarDuration.Long
              )
          )

      }

    }
    }

}