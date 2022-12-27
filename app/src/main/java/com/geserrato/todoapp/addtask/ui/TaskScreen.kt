package com.geserrato.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.geserrato.todoapp.addtask.ui.model.TaskModel


@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    val showDialog: Boolean by taskViewModel.isShowDialog.observeAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        AddTaskDialog(showDialog,
            onDismiss = { taskViewModel.onDismissDialog() },
            onTaskAdded = { taskViewModel.onTaskAdded(it) }
        )
        FabDialog(taskViewModel, modifier = Modifier.align(Alignment.BottomEnd))
        TaskList(taskViewModel)
    }
}

@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val myTasks: List<TaskModel> = taskViewModel.tasks
    LazyColumn {
        items(myTasks, key = { it.id }) { itemTask ->
            ItemTask(taskModel = itemTask, taskViewModel = taskViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = taskModel.task, modifier = Modifier.weight(1f))
            Checkbox(
                checked = taskModel.done,
                onCheckedChange = { taskViewModel.onCheckboxSelected(taskModel) })
        }
    }

}

@Composable
fun FabDialog(taskViewModel: TaskViewModel, modifier: Modifier) {
    FloatingActionButton(
        onClick = { taskViewModel.showDialog() },
        modifier = modifier
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTask,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { myTask = it },
                    singleLine = true
                )
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }
}