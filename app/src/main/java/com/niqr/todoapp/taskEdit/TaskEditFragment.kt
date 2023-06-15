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
import com.niqr.todoapp.utils.toStringDate
import kotlinx.coroutines.flow.collectLatest
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
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFlows()
        setupDatePicker(view)
        setupPriorityMenu(view)
        setupButtons(view)

    }

    private fun setupFlows() {
        val dateText = requireView().findViewById<TextView>(R.id.dateText)
        val priorityText = requireView().findViewById<TextView>(R.id.priorityText)
        lifecycleScope.launch {
            viewModel.date.collectLatest {
                dateText.text = it?.toStringDate()
            }
        }
        lifecycleScope.launch {
            viewModel.priority.collectLatest {
                priorityText.text = getText(it.resId)
            }
        }
    }

    private fun setupDatePicker(view: View) {
        val switch = view.findViewById<SwitchCompat>(R.id.date_picker_switch)

        val picker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .build()

        switch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked) {
                true -> picker.show(childFragmentManager, null)
                false -> {
                    val dateText = view.findViewById<TextView>(R.id.dateText)
                    dateText.text = ""
                }
            }
        }

        picker.addOnNegativeButtonClickListener { switch.isChecked = false }
        picker.addOnCancelListener { switch.isChecked = false }
        picker.addOnPositiveButtonClickListener(viewModel::updateDate)
    }

    private fun setupPriorityMenu(view: View) {
        val priorityField = view.findViewById<LinearLayout>(R.id.priorityField)

        val priorityMenu = androidx.appcompat.widget.PopupMenu(view.context, priorityField)
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

    private fun setupButtons(view: View) {
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)
        val saveButton = view.findViewById<TextView>(R.id.saveButton)
        val deleteButton = view.findViewById<LinearLayout>(R.id.deleteButton)

        closeButton.setOnClickListener { parentFragmentManager.popBackStack() }
        saveButton.setOnClickListener { parentFragmentManager.popBackStack() }
        deleteButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}