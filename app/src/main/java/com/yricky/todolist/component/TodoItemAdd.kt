package com.yricky.todolist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yricky.todolist.model.DataBaseModel

/**
 * @author Yricky
 * @date 2021/11/19
 */

@Composable
fun TodoAdd(viewModel: TodoItemAddViewModel,onAdd:(()->Unit)? = null){
    var title by remember {  mutableStateOf("")  }
    var content by remember {  mutableStateOf("")  }
    var priority by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(0.dp,8.dp),
            value = title,
            onValueChange = {
            title = it
            viewModel.item.value?.title = title
        })

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = content,
            onValueChange = {
            content = it
            viewModel.item.value?.content = content
        })

        Slider(
            modifier = Modifier.widthIn(max = 700.dp),
            value = priority,
            onValueChange = {
                priority = it
                viewModel.item.value?.priority = (priority*2).toInt() },
            steps = 1
        )

        Button(onClick = {
            viewModel.item.value?.let{
                DataBaseModel.insert(it){
                    onAdd?.invoke()
                }
            }
        }) {
            Text(text = "done")
        }
    }



}