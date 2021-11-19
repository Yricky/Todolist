package com.yricky.todolist

import android.app.Application
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author Yricky
 * @date 2021/11/19
 */
class TodoApp:Application() {
    companion object{
        lateinit var inst:TodoApp
        val tpe: ThreadPoolExecutor by lazy {
            ThreadPoolExecutor(1,8,10L, TimeUnit.MILLISECONDS, LinkedBlockingQueue(12))
        }
    }

    override fun onCreate() {
        super.onCreate()
        inst = this
    }
}

val globalCxt:TodoApp get() = TodoApp.inst