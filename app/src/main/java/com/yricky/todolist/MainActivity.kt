package com.yricky.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.yricky.todolist.component.MainList
import com.yricky.todolist.component.MainListViewModel
import com.yricky.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    private val viewModel:MainListViewModel by lazy{
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
                    Scaffold(topBar = { TopAppBar{ TopBar() } }) {
                        Box {
                            MainList(viewModel = viewModel)
                            Column(
                                Modifier.fillMaxWidth().fillMaxHeight(),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier.padding(16.dp),
                                    onClick = { activityLauncher.launch(Intent(this@MainActivity,TodoAddActivity::class.java)) },
                                    icon = {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Favorite"
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
        viewModel.refreshList()
    }

    @Composable
    fun TopBar(){
        Row(
            Modifier
                .height(48.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = getString(R.string.app_name), fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))
        }

    }


}