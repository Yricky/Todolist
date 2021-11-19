package com.yricky.todolist.component

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yricky.todolist.model.pojo.TodoItem

/**
 * @author Yricky
 * @date 2021/11/19
 */
class TodoItemAddViewModel:ViewModel() {
    val item:MutableLiveData<TodoItem> by lazy{
        MutableLiveData<TodoItem>(TodoItem(0))
    }
}