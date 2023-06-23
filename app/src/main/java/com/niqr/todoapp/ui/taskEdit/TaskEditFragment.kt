package com.niqr.todoapp.ui.taskEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.niqr.todoapp.R
import com.niqr.todoapp.compose.ui.taskEdit.TaskEditViewModel
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.compose.ui.taskEdit.model.TaskEditEvent
import com.niqr.todoapp.utils.toStringResource
import com.niqr.todoapp.utils.DAY
import com.niqr.todoapp.utils.toStringDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ARG_TASK_ID = "id"

@AndroidEntryPoint
class TaskEditFragment : Fragment() {
    private val viewModel by viewModels<TaskEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(ARG_TASK_ID)?.let { taskId ->
                viewModel.setupTask(taskId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiEventsListener()
        setupFlows()
        setupTextField()
        setupDatePicker()
        setupDateSwitch()
        setupPriorityMenu()
        setupButtons()
    }

    private fun setupUiEventsListener() {
        lifecycleScope.launch {
            viewModel.uiEvent.collectLatest {
                when(it) {
                    TaskEditEvent.NavigateBack -> parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun setupFlows() {
        val view = requireView()
        val editText = view.findViewById<TextInputEditText>(R.id.editText)
        val dateText = view.findViewById<TextView>(R.id.dateText)
        val priorityText = view.findViewById<TextView>(R.id.priorityText)
        val dateField = view.findViewById<LinearLayout>(R.id.editDate)
        val switch = view.findViewById<SwitchCompat>(R.id.date_switch)

        lifecycleScope.launch {
            viewModel.description.collect {
                editText.setTextKeepState(it)
            }
        }
        lifecycleScope.launch {
            viewModel.date.collect {
                dateText.text = it.toStringDate()
            }
        }
        lifecycleScope.launch {
            viewModel.priority.collect {
                priorityText.text = getText(it.toStringResource())
            }
        }
        lifecycleScope.launch {
            viewModel.dateVisibility.collect {
                dateField.isClickable = it
                dateText.text = if (it) viewModel.date.value.toStringDate() else ""
                switch.isChecked = it
            }
        }
    }

    private fun setupTextField() {
        val editText = requireView().findViewById<TextInputEditText>(R.id.editText)
        editText.addTextChangedListener {
            viewModel.updateDescription(it.toString())
        }
    }

    private fun setupDatePicker() {
        val editDate = requireView().findViewById<LinearLayout>(R.id.editDate)
        val switch = requireView().findViewById<SwitchCompat>(R.id.date_switch)
        val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds() + DAY)
                .build()

        editDate.setOnClickListener {
            if (!picker.isVisible)
                picker.show(childFragmentManager, null)
        }
        editDate.isClickable = switch.isChecked

        picker.addOnPositiveButtonClickListener(viewModel::updateDate)
    }

    private fun setupDateSwitch() {
        val switch = requireView().findViewById<SwitchCompat>(R.id.date_switch)

        switch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDateVisibility(isChecked)
        }
    }

    private fun setupPriorityMenu() {
        val priorityField = requireView().findViewById<LinearLayout>(R.id.priorityField)

        val priorityMenu = androidx.appcompat.widget.PopupMenu(requireContext(), priorityField)
        priorityMenu.inflate(R.menu.priority_menu)
        priorityField.setOnClickListener {
            priorityMenu.show()
        }
        priorityMenu.setOnMenuItemClickListener {
            val priority = when(it.itemId) {
                R.id.priority_menu_low -> Priority.LOW
                R.id.priority_menu_high -> Priority.HIGH
                else -> Priority.COMMON
            }
            viewModel.updatePriority(priority)
            true
        }
    }

    private fun setupButtons() {
        val view = requireView()
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)
        val saveButton = view.findViewById<TextView>(R.id.saveButton)
        val deleteButton = view.findViewById<TextView>(R.id.deleteButton)

        closeButton.setOnClickListener { parentFragmentManager.popBackStack() }
        saveButton.setOnClickListener { viewModel.saveTask() }
        deleteButton.setOnClickListener { viewModel.deleteTask() }
    }

    companion object {
        @JvmStatic
        fun newInstance(task: String) =
            TaskEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TASK_ID, task)
                }
            }
    }
}