package com.yricky.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.lifecycle.ViewModelProvider
import com.yricky.todolist.component.MainList
import com.yricky.todolist.component.MainListViewModel
import com.yricky.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    val viewModel:MainListViewModel by lazy{
        ViewModelProvider(this)[MainListViewModel::class.java]
    }
    private val activityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.refreshList()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodolistTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainList(viewModel = viewModel)
                    Button(onClick = {
                        activityLauncher.launch(Intent(this,TodoAddActivity::class.java))
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
        viewModel.refreshList()
    }



}