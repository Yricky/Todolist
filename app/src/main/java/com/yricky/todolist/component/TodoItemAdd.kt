package com.yricky.todolist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val scroll = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 700.dp)
                .fillMaxSize()
                .verticalScroll(scroll),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                value = title,
                maxLines = 1,
                label = { Text(text = "Title") },
                onValueChange = {
                    title = it.trim()
                    viewModel.item.value?.title = title
                })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                label = { Text(text = "Content") },
                value = content,
                onValueChange = {
                    content = it
                    viewModel.item.value?.content = content
                })
            Text(text = "Priority:"+when((priority*2).toInt()){
                0->"Low"
                1->"Medium"
                2->"High"
                else ->""
            },
                fontSize = 18.sp
            )
            Slider(
                modifier = Modifier.widthIn(max = 700.dp),
                value = priority,
                onValueChange = {
                    priority = it
                    viewModel.item.value?.priority = (priority*2).toInt() },
                steps = 1
            )
        }
    }




}