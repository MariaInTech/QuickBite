package com.example.quickbite.com.example.quickbite.Db_handler
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class CartItemDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun deleteAllItems() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "CartItem.db"

        const val TABLE_NAME = "cart_items"
        const val COLUMN_ITEM_ID = "item_id"
        const val COLUMN_RESTAURANT_NAME = "restaurant_name"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE_URL = "image_url"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_ITEM_ID TEXT," +
                    "$COLUMN_RESTAURANT_NAME TEXT," +
                    "$COLUMN_ITEM_NAME TEXT," +
                    "$COLUMN_QUANTITY INTEGER," +
                    "$COLUMN_PRICE REAL," +
                    "$COLUMN_IMAGE_URL TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
