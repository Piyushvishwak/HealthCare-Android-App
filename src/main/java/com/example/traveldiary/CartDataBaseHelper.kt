package com.example.traveldiary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CartDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cart.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CART = "cart"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PACKAGE_NAME = "package_name"
        private const val COLUMN_PACKAGE_DETAILS = "package_details"
        private const val COLUMN_PACKAGE_PRICE = "package_price"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_CART ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_PACKAGE_NAME TEXT,"
                + "$COLUMN_PACKAGE_DETAILS TEXT,"
                + "$COLUMN_PACKAGE_PRICE TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        onCreate(db)
    }

    fun addCartItem(item: HashMap<String, String>) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PACKAGE_NAME, item["PackageName"])
            put(COLUMN_PACKAGE_DETAILS, item["PackageDetails"])
            put(COLUMN_PACKAGE_PRICE, item["PackagePrice"])
        }
        db.insert(TABLE_CART, null, values)
        db.close()
    }

    fun getAllCartItems(): ArrayList<HashMap<String, String>> {
        val cartItems = ArrayList<HashMap<String, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CART", null)
        if (cursor.moveToFirst()) {
            do {
                val item = HashMap<String, String>()
                item["PackageName"] = cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_NAME))
                item["PackageDetails"] = cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_DETAILS))
                item["PackagePrice"] = cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_PRICE))
                cartItems.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cartItems
    }

    fun deleteAllCartItems() {
        val db = this.writableDatabase
        db.delete(TABLE_CART, null, null)
        db.close()
    }

    fun deleteCartItem(packageName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_CART, "$COLUMN_PACKAGE_NAME=?", arrayOf(packageName))
        db.close()
    }
}
