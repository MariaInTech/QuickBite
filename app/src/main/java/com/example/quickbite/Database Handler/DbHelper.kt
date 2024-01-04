package com.example.quickbite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle upgrades if needed
    }

    companion object {
        private const val DATABASE_NAME = "user_credentials.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"

        // SQL statement to create the users table
        private const val DATABASE_CREATE = ("create table "
                + TABLE_USERS + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_USERNAME
                + " text not null, " + COLUMN_PASSWORD
                + " text not null);")
    }
}