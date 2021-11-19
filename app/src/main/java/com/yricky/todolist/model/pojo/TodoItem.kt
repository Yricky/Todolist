package com.yricky.todolist.model.pojo

import android.provider.BaseColumns

/**
 * @author Yricky
 * @date 2021/11/19
 */
class TodoItem(
    val id:Long,
    var title:String = "title",
    var content:String = "content",
    var timeStamp:Long = System.currentTimeMillis(),
    var priority:Int = 0,
    var done:Boolean = false
    ):BaseColumns