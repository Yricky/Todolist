package com.yricky.todolist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yricky.todolist.R
import com.yricky.todolist.model.DataBaseModel
import com.yricky.todolist.model.pojo.TodoItem
import java.util.*

/**
 * @author Yricky
 * @date 2021/11/19
 */

@Composable
fun MainList(viewModel:MainListViewModel){
    val listItems by viewModel.todoListItem.observeAsState()
    listItems?.let{
        LazyColumn{
            items(it){ todoItem ->
                TodoListItem(todoItem,viewModel)
            }
        }
    }
}

@Preview(showBackground = true,backgroundColor = 0xffffffff)
@Composable
private fun TodoListItem(item:TodoItem = TodoItem(0),viewModel: MainListViewModel? = null){
    var isChecked:Boolean by remember {
        mutableStateOf(item.done)
    }
    isChecked = item.done
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .widthIn(max = 700.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.width(36.dp),
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    item.done = isChecked
                    viewModel?.saveItem(item)
                })
            Column(Modifier.width(628.dp)) {
                Text(
                    text = item.title,
                    fontSize = if(item.priority>0) 20.sp else 18.sp,
                    color = if(item.priority > 1) Color(0xffcc6666) else Color(0xff999999)
                )
                Text(text = item.content)
                Text(text = Date(item.timeStamp).toString())
            }
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                modifier = Modifier.clickable { viewModel?.deleteItem(item) }.width(36.dp),
                contentDescription = "",
            )
        }
    }

}