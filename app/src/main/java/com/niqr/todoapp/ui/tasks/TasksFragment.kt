package com.niqr.todoapp.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.niqr.todoapp.R
import com.niqr.todoapp.compose.ui.tasks.TasksViewModel
import com.niqr.todoapp.ui.taskEdit.TaskEditFragment
import com.niqr.todoapp.compose.ui.tasks.model.TasksAction
import com.niqr.todoapp.compose.ui.tasks.model.TasksEvent
import com.niqr.todoapp.ui.tasks.recycler.SwipeTodoItemCallback
import com.niqr.todoapp.ui.tasks.recycler.TodoItemDecoration
import com.niqr.todoapp.ui.tasks.recycler.TodoItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment() {
    private val viewModel by viewModels<TasksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiEventsListener()
        setupDoneVisibilityButton()
        setupRecyclerAndCounter()
        setupNavigationToNewItem()
    }

    private fun setupUiEventsListener() {
        lifecycleScope.launch {
            viewModel.uiEvent.collectLatest {
                when(it) {
                    is TasksEvent.NavigateToEditTask -> {
                        navigateToEditTask(it.id)
                    }
                    TasksEvent.NavigateToNewTask -> {
                        navigateToNewTask()
                    }
                }
            }
        }
    }

    private fun setupDoneVisibilityButton() {
        val view = requireView()
        val visibilityButton = view.findViewById<CheckBox>(R.id.visibilityButton)

        visibilityButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAction( TasksAction.UpdateDoneVisibility(isChecked) )
        }
    }

    private fun setupRecyclerAndCounter() {
        val view = requireView()
        val counter = view.findViewById<TextView>(R.id.doneCounter)

        val todoItemsRecyclerView = view.findViewById<RecyclerView>(R.id.todoItems)
        val todoItemsAdapter = TodoItemsAdapter(viewModel::onAction)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        todoItemsRecyclerView.adapter = todoItemsAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemsRecyclerView.addItemDecoration(
            TodoItemDecoration(view.context, R.drawable.recycler_divider)
        )
        setupRecyclerViewSwipes(todoItemsAdapter, todoItemsRecyclerView)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.todoItems().collect {
                        todoItemsAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.doneCount().collect {
                        counter.text = getString(R.string.done_count, it)
                    }
                }
            }
        }
    }

    private fun setupRecyclerViewSwipes(adapter: TodoItemsAdapter, recyclerView: RecyclerView) {
        val swipeCallback = SwipeTodoItemCallback(
            onSwipeLeft = { position ->
                val item = adapter.getItem(position)
                viewModel.onAction(TasksAction.DeleteTask(item.id))
            },
            onSwipeRight = { position ->
                val item = adapter.getItem(position)
                val newItem = item.copy(isDone = !item.isDone)
                viewModel.onAction(TasksAction.UpdateTask(newItem))
            },
            applicationContext = requireActivity().baseContext
        )

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupNavigationToNewItem() {
        val view = requireView()
        val fab = view.findViewById<FloatingActionButton>(R.id.newItemFab)

        fab.setOnClickListener { navigateToNewTask() }
    }

    private fun navigateToEditTask(id: String) {
        parentFragmentManager.commit {
            val fragment = TaskEditFragment.newInstance(id)
            setReorderingAllowed(true)
            replace(R.id.fragment, fragment)
            addToBackStack(null)
        }
    }
    private fun navigateToNewTask() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<TaskEditFragment>(R.id.fragment)
            addToBackStack(null)
        }
    }
}