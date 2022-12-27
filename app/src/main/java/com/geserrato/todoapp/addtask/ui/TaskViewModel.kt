package com.geserrato.todoapp.addtask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geserrato.todoapp.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    private val _isShowDialog = MutableLiveData<Boolean>()
    val isShowDialog: LiveData<Boolean> = _isShowDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun showDialog() {
        _isShowDialog.value = true
    }

    fun onDismissDialog() {
        _isShowDialog.value = false
    }

    fun onTaskAdded(task: String) {
        _tasks.add(TaskModel(task = task))
        _isShowDialog.value = false
    }

    fun onCheckboxSelected(taskModel: TaskModel) {
        val idx = _tasks.indexOf(taskModel)
        _tasks[idx] = _tasks[idx].let {
            it.copy(done = !it.done)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }
}