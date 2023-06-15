package com.niqr.todoapp.taskEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.niqr.todoapp.R
import com.niqr.todoapp.taskEdit.model.Priority
import com.niqr.todoapp.utils.DAY
import com.niqr.todoapp.utils.toStringDate
import kotlinx.coroutines.launch


class TaskEditFragment : Fragment() {
    private val viewModel by viewModels<TaskEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFlows()
        setupDatePicker()
        setupDateSwitch()
        setupPriorityMenu()
        setupButtons()

    }

    private fun setupFlows() {
        val view = requireView()
        val dateText = view.findViewById<TextView>(R.id.dateText)
        val priorityText = view.findViewById<TextView>(R.id.priorityText)
        val dateField = view.findViewById<LinearLayout>(R.id.editDate)

        lifecycleScope.launch {
            viewModel.date.collect {
                dateText.text = it.toStringDate()
            }
        }
        lifecycleScope.launch {
            viewModel.priority.collect {
                priorityText.text = getText(it.resId)
            }
        }
        lifecycleScope.launch {
            viewModel.dateVisibility.collect {
                dateField.isClickable = it
                dateText.text = if (it) viewModel.date.value.toStringDate() else ""
            }
        }
    }

    private fun setupDatePicker() {
        val editDate = requireView().findViewById<LinearLayout>(R.id.editDate)
        val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds() + DAY)
                .build()

        editDate.setOnClickListener {
            if (!picker.isVisible)
                picker.show(childFragmentManager, null)
        }
        editDate.isClickable = false

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
                R.id.priority_menu_low -> Priority.Low
                R.id.priority_menu_high -> Priority.High
                else -> Priority.No
            }
            viewModel.updatePriority(priority)
            true
        }
    }

    private fun setupButtons() {
        val view = requireView()
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)
        val saveButton = view.findViewById<TextView>(R.id.saveButton)
        val deleteButton = view.findViewById<LinearLayout>(R.id.deleteButton)

        closeButton.setOnClickListener { parentFragmentManager.popBackStack() }
        saveButton.setOnClickListener { parentFragmentManager.popBackStack() }
        deleteButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}