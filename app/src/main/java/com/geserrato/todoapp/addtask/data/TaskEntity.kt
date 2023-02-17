package com.geserrato.todoapp.addtask.data

import androidx.room.PrimaryKey

data class TaskEntity(
    @PrimaryKey
    val id: Long,
    val task: String,
    var done: Boolean = false
)
