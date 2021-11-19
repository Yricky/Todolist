package com.yricky.todolist.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.yricky.todolist.TodoApp
import com.yricky.todolist.globalCxt
import com.yricky.todolist.model.pojo.TodoItem
import java.io.File

/**
 * @author Yricky
 * @date 2021/11/19
 */
object DataBaseModel {
    const val TABLE_NAME = "items"
    const val CLM_TITLE = "tit"
    const val CLM_CONTENT = "ctt"
    const val CLM_PRIORITY = "prt"
    const val CLM_TIME = "time"
    const val CLM_DONE = "done"
    const val SQL_CMD_CRE = "CREATE TABLE $TABLE_NAME(" +
            "$_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$CLM_TITLE TEXT NOT NULL," +
            "$CLM_CONTENT TEXT NOT NULL," +
            "$CLM_TIME INTEGER NOT NULL," +
            "$CLM_PRIORITY INTEGER NOT NULL" +
            ");"

    private val cacheDB = DBHelper.writableDatabase
    private val empty:List<TodoItem> = listOf()
    private object DBHelper: SQLiteOpenHelper(
        globalCxt,
        File(globalCxt.getExternalFilesDir(null), "items.db").path, null,3) {


        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(SQL_CMD_CRE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if(oldVersion == 2 && newVersion == 3)
                db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $CLM_DONE INTEGER")
        }
    }

    fun update(entries: TodoItem, then:Runnable? = null){
        TodoApp.tpe.execute {
            entries.let {
                cacheDB.execSQL(
                    "INSERT OR REPLACE INTO $TABLE_NAME (" +
                            "${_ID}," +
                            "${CLM_TITLE}," +
                            "${CLM_CONTENT}," +
                            "${CLM_TIME}," +
                            "$CLM_PRIORITY," +
                            "$CLM_DONE " +
                            ") " +
                            "VALUES (?,?,?,?,?,?)",
                    arrayOf(
                        it.id,
                        it.title,
                        it.content,
                        it.timeStamp,
                        it.priority,
                        if(it.done) 1 else 0
                    )
                )

            }
            then?.run()
        }
    }

    fun insert(entries: TodoItem, then:Runnable? = null){
        TodoApp.tpe.execute {
            entries.let {
                cacheDB.insert(TABLE_NAME,null, ContentValues().apply {
                put(CLM_TITLE,it.title)
                put(CLM_CONTENT,it.content)
                put(CLM_TIME,it.timeStamp)
                put(CLM_PRIORITY,it.priority)
                put(CLM_DONE,if(it.done) 1 else 0)
            })
            }
            then?.run()
        }

    }

    fun delete(entries: TodoItem,then:Runnable? = null){
        TodoApp.tpe.execute {
            entries.let {
                cacheDB.delete(TABLE_NAME,"$_ID=?", arrayOf("${it.id}"))
            }
            then?.run()
        }
    }

    @SuppressLint("Range")
    fun getList():List<TodoItem>{
        val cursor = cacheDB.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$CLM_DONE ASC , $CLM_PRIORITY DESC"
        )
        cursor.moveToFirst()
        val listSize = cursor.count.coerceAtLeast(0)
        val list = ArrayList<TodoItem>(listSize).apply {
            (0 until listSize).forEach{ _ ->
                add(with(cursor){
                    TodoItem(
                        id = getLong(getColumnIndex(_ID)),
                        title = getString(getColumnIndex(CLM_TITLE)),
                        content = getString(getColumnIndex(CLM_CONTENT)),
                        timeStamp = getLong(getColumnIndex(CLM_TIME)),
                        priority = getInt(getColumnIndex(CLM_PRIORITY)),
                        done = getInt(getColumnIndex(CLM_DONE)) == 1,
                    )
                })
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }
}