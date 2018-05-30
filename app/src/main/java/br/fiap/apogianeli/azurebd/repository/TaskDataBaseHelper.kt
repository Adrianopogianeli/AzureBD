package br.fiap.apogianeli.azurebd.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.fiap.apogianeli.azurebd.constants.DataBaseConstants


class TaskDataBaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "task.db"
    }


    private val createTableUser = """
        CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} {
        ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.USER.COLUMNS.NAME} TEXT,
        ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT,
        ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
        };
        """

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"

    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Remove
        sqlLite.execSQL(deleteTableUser)

        // cria
        sqlLite.execSQL(createTableUser)
    }



}