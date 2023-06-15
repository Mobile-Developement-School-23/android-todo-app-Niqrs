package com.niqr.todoapp

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date


private const val ARG_TASK_ID = "task"

class TaskEditFragment : Fragment() {
    private var task: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            task = it.getString(ARG_TASK_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_edit, container, false)

        setupDatePicker(view)
        setupPriorityMenu(view)
        setupButtons(view)

        return view
    }

    private fun setupDatePicker(view: View) {
        val picker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .build()

        val switch = view.findViewById<SwitchCompat>(R.id.date_picker_switch)
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
        picker.addOnPositiveButtonClickListener {
            val dateString: String = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG).format(Date(it))
            val dateText = view.findViewById<TextView>(R.id.dateText)
            dateText.text = dateString
        }
    }

    private fun setupPriorityMenu(view: View) {
        val priorityField = view.findViewById<LinearLayout>(R.id.priorityField)
        val priorityText = view.findViewById<TextView>(R.id.priorityText)

        val priorityMenu = androidx.appcompat.widget.PopupMenu(view.context, priorityField)
        priorityMenu.inflate(R.menu.priority_menu)
        priorityField.setOnClickListener {
            priorityMenu.show()
        }
        priorityMenu.setOnMenuItemClickListener {
            priorityText.text = it.title
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