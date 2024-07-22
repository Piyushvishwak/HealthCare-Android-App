package com.example.traveldiary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val qry = "CREATE TABLE users(username TEXT, email TEXT, password TEXT)"
        db.execSQL(qry)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun register(username: String, email: String, password: String) {
        val cv = ContentValues().apply {
            put("username", username)
            put("email", email)
            put("password", password)
        }

        val db = writableDatabase
        db.insert("users", null, cv)
        db.close()
    }

    fun login(username: String, password: String): Int {
        var result = 0
        val db = readableDatabase
        val selectionArgs = arrayOf(username, password)
        val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", selectionArgs)

        if (cursor.moveToFirst()) {
            result = 1
        }

        cursor.close()
        db.close()
        return result
    }
}
