package com.yricky.todolist.component

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yricky.todolist.TodoApp
import com.yricky.todolist.model.DataBaseModel
import com.yricky.todolist.model.pojo.TodoItem

/**
 * @author Yricky
 * @date 2021/11/19
 */
class MainListViewModel:ViewModel() {
    private val handler: Handler = Handler(Looper.getMainLooper())
    val todoListItem:MutableLiveData<List<TodoItem>> by lazy{
        MutableLiveData();
    }

    fun saveItem(item:TodoItem){
        TodoApp.tpe.execute {
            DataBaseModel.update(item){
                handler.post { todoListItem.value = DataBaseModel.getList() }
            }
        }
    }

    fun deleteItem(item:TodoItem){
        TodoApp.tpe.execute {
            DataBaseModel.delete(item){
                handler.post { todoListItem.value = DataBaseModel.getList() }
            }
        }
    }

    fun refreshList(){
        TodoApp.tpe.execute {
            val list = DataBaseModel.getList()
            handler.post { todoListItem.value = list }
        }
    }
}