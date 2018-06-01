package br.fiap.apogianeli.azurebd.repository

import android.content.Context
import android.database.Cursor
import br.fiap.apogianeli.azurebd.constants.DataBaseConstants
import br.fiap.apogianeli.azurebd.entities.PriorityEntity

class PriorityRepository private constructor(context: Context){

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context) : PriorityRepository {

            if(INSTANCE == null){
                INSTANCE = PriorityRepository(context)
            }
            return INSTANCE as PriorityRepository

        }

        private var INSTANCE: PriorityRepository? = null
    }

    // func para retornar valor para o spinner
    fun getList(): MutableList<PriorityEntity>{

        val list = mutableListOf<PriorityEntity>()
        try {
            val cursor: Cursor
            val db = this.mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("select * from ${DataBaseConstants.PRIORITY.TABLE_NAME}",null)

            if (cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION))

                    val guestEntity = PriorityEntity(id, description)

                    list.add(guestEntity)
                }
            }
            cursor.close()

        }catch (e: Exception) {
            return list
        }

        return list

    }

}