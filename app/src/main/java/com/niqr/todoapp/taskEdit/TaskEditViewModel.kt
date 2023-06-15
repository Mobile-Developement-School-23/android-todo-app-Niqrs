package com.niqr.todoapp.taskEdit

import androidx.lifecycle.ViewModel
import com.niqr.todoapp.R
import com.niqr.todoapp.taskEdit.model.Priority
import com.niqr.todoapp.utils.UiText
import com.niqr.todoapp.utils.dateFromLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(): ViewModel() {

    private val _priority = MutableStateFlow(UiText.StringResource(R.string.priority_no))
    val priority = _priority.asStateFlow()

    private val _date = MutableStateFlow<LocalDate>(LocalDate.now().plusDays(1))
    val date = _date.asStateFlow()

    private val _dateVisibility = MutableStateFlow(false)
    val dateVisibility = _dateVisibility.asStateFlow()

    fun updatePriority(priority: Priority) {
        val priorityResource = when(priority) {
            Priority.No -> R.string.priority_no
            Priority.Low -> R.string.priority_low
            Priority.High -> R.string.priority_high
        }
        _priority.update {
            UiText.StringResource(priorityResource)
        }
    }

    fun updateDate(time: Long) {
        val newDate = dateFromLong(time)
        _date.update {
            newDate
        }
    }

    fun updateDateVisibility(visible: Boolean) {
        _dateVisibility.update {
            visible
        }
    }
}