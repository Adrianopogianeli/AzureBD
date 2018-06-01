package br.fiap.apogianeli.azurebd.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.fiap.apogianeli.azurebd.constants.DataBaseConstants
import br.fiap.apogianeli.azurebd.entities.TaskEntity


class TaskRepository private constructor(context: Context) {


    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context) : TaskRepository {

            if(INSTANCE == null){
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository

        }

        private var INSTANCE: TaskRepository? = null
    }


    // get, update, insert e delete
    fun get (id: Int) : TaskEntity? {

        var taskEntity: TaskEntity? = null

        try {

            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.TASK.COLUMNS.ID,
                    DataBaseConstants.TASK.COLUMNS.USERID,
                    DataBaseConstants.TASK.COLUMNS.PRIORITYID,
                    DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                    DataBaseConstants.TASK.COLUMNS.DUETATE,
                    DataBaseConstants.TASK.COLUMNS.COMPLETE
                    )

            // clausula where
            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            // Array das colunas do where
            val selectionArgs = arrayOf(id.toString())

            // select do email
            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection,selection,selectionArgs, null, null, null)

            if (cursor.count > 0){
                cursor.moveToFirst()

                val taskid = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUETATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE))==1)

                // alimentado a entidade task
                taskEntity = TaskEntity(id, userId,priorityId,description,dueDate,complete)
            }

            cursor.close()
        }
        catch (e: Exception){
            return taskEntity
        }
        return taskEntity

    }

    // insert
    fun insert(task: TaskEntity) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userid)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUETATE, task.dueDate)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            val result = db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues)
            println("Segue o valor do insert " + result.toString())

        }catch (e: Exception){
            throw e
        }


    }

    // update
    fun update(task: TaskEntity) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userid)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUETATE, task.dueDate)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            val where = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(task.id.toString())

            val result = db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, where, whereArgs)
            println("Segue o valor do update " + result.toString())

        }catch (e: Exception){
            throw e
        }

    }

    //delete
    fun delete(id: Int){

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(id.toString())

            val result = db.delete(DataBaseConstants.TASK.TABLE_NAME, where, whereArgs)
            println("Segue o valor do delete " + result.toString())

        }catch (e: Exception){
            throw e
        }

    }

    // func para retornar valor para o spinner
    fun getList(userId: Int) : MutableList<TaskEntity>{

        val list = mutableListOf<TaskEntity>()
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("select * from ${DataBaseConstants.TASK.TABLE_NAME} ) " +
            " WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userId",null)

            if (cursor.count > 0){
                while (cursor.moveToNext()){

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUETATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE))==1)

                    list.add(TaskEntity(id, userId,priorityId,description,dueDate,complete))
                }
            }
            cursor.close()

        }catch (e: Exception) {
            return list
        }

        return list

    }
}