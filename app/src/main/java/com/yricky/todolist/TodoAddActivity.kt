package com.yricky.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.yricky.todolist.component.MainList
import com.yricky.todolist.component.TodoAdd
import com.yricky.todolist.component.TodoItemAddViewModel
import com.yricky.todolist.model.DataBaseModel
import com.yricky.todolist.ui.theme.TodolistTheme

class TodoAddActivity : ComponentActivity() {
    val viewModel:TodoItemAddViewModel by lazy{
        ViewModelProvider(this)[TodoItemAddViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodolistTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(topBar = { TopAppBar{ TopBar() } }) {
                        Box {
                            TodoAdd(viewModel = viewModel)
                            Column(
                                Modifier.fillMaxWidth().fillMaxHeight(),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier.padding(16.dp),
                                    onClick = {
                                        viewModel.item.value?.let{
                                            DataBaseModel.insert(it){
                                                setResult(Activity.RESULT_OK)
                                                finish()
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            Icons.Filled.Check,
                                            contentDescription = ""
                                        )
                                    },
                                    text = { Text("Add") }
                                )
                            }

                        }
                    }

                }
            }
        }
    }

    @Composable
    fun TopBar(){
        Row(
            Modifier
                .height(48.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Add todo", fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}