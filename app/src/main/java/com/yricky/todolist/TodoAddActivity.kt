package com.yricky.todolist

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.yricky.todolist.component.TodoAdd
import com.yricky.todolist.component.TodoItemAddViewModel
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
                    TodoAdd(viewModel = viewModel){
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
        }
    }
}