package com.niqr.todoapp.ui.tasks.recycler

import android.content.res.Resources.Theme
import android.graphics.Paint
import android.util.TypedValue
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.niqr.todoapp.R
import com.niqr.todoapp.data.model.Priority
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.compose.ui.tasks.model.TasksAction
import com.niqr.todoapp.utils.toResource
import com.niqr.todoapp.utils.toStringDate


class TodoItemViewHolder(
    itemView: View,
): RecyclerView.ViewHolder(itemView) {
    private lateinit var todoItem: TodoItem

    private val taskCheck: CheckBox = itemView.findViewById(R.id.taskCheck)
    private val priorityIcon: ImageView = itemView.findViewById(R.id.priorityIcon)
    private val textField: LinearLayout = itemView.findViewById(R.id.textField)
    private val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    private val dateText: TextView = itemView.findViewById(R.id.dateText)
    private val infoIcon: ImageView = itemView.findViewById(R.id.infoIcon)

    fun onBind(todoItem: TodoItem, onUiAction: (TasksAction) -> Unit) {
        this.todoItem = todoItem
        setupValues()
        setupCallback(onUiAction)
    }

    private fun setupValues() {
        with(todoItem) {
            taskCheck.isChecked = isDone
            priorityIcon.setImageResource(todoItem.priority.toResource())
            descriptionText.text = description
            dateText.text = deadline?.toStringDate() ?: ""

            val context = itemView.context
            val theme = context.theme

            setupCheckboxView(priority)
            setupDescriptionView(isDone, theme)
        }
    }

    private fun setupCheckboxView(priority: Priority) {
        taskCheck.apply {
            if (priority == Priority.HIGH) {
                val tint = ContextCompat.getColorStateList(context, R.color.checkbox_extra_tint)
                val drawable = getDrawable(context, R.drawable.checkbox_extra_drawable)
                buttonDrawable = drawable
                buttonTintList = tint
            } else {
                val tint = ContextCompat.getColorStateList(context, R.color.checkbox_normal_tint)
                buttonTintList = tint
            }
        }
    }

    private fun setupDescriptionView(isDone: Boolean, theme: Theme) {
        descriptionText.apply {
            val typedValue = TypedValue()
            if (isDone) {
                if((paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) != Paint.STRIKE_THRU_TEXT_FLAG)
                    paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
                theme.resolveAttribute(R.attr.label_tertiary, typedValue, true);
                setTextColor(typedValue.data)
            } else {
                if((paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG)
                    paintFlags -= Paint.STRIKE_THRU_TEXT_FLAG
                theme.resolveAttribute(R.attr.label_primary, typedValue, true);
                setTextColor(typedValue.data)
            }
        }
    }

    private fun setupCallback(onUiAction: (TasksAction) -> Unit) {
        val context = itemView.context
        taskCheck.setOnCheckedChangeListener { _, isChecked ->
            if (todoItem.isDone != isChecked) {
                val newItem = todoItem.copy(isDone = isChecked)
                onUiAction(TasksAction.UpdateTask(newItem))
            }
        }

        val itemMenu = PopupMenu(context, textField)
        itemMenu.inflate(R.menu.task_menu)
        setupMenuItemsCallbacks(itemMenu, onUiAction)
        textField.setOnLongClickListener {
            itemMenu.show()
            true
        }
    }

    private fun setupMenuItemsCallbacks(itemMenu: PopupMenu, onUiAction: (TasksAction) -> Unit) {
        itemMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.task_menu_edit -> {
                    onUiAction(TasksAction.EditTask(todoItem))
                }
            }
            true
        }
    }
}