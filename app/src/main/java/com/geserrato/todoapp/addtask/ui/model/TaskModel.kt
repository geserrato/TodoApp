package com.geserrato.todoapp.addtask.ui.model

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var done: Boolean = false
)