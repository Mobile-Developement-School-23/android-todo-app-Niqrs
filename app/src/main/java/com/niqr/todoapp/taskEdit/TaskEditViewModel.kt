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
    val priority: StateFlow<UiText.StringResource>
        get() = _priority.asStateFlow()

    private val _date = MutableStateFlow<LocalDate?>(null)
    val date = _date.asStateFlow()

    fun updatePriority(priority: Priority) {
        val priorityResource = when(priority) {
            Priority.No -> R.id.priority_menu_no
            Priority.Low -> R.id.priority_menu_low
            Priority.High -> R.id.priority_menu_high
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
}