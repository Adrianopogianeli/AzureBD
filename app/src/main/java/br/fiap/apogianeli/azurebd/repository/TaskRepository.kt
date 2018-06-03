package br.fiap.apogianeli.azurebd.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.widget.Toast
import br.fiap.apogianeli.azurebd.constants.DataBaseConstants
import br.fiap.apogianeli.azurebd.entities.TaskEntity


class TaskRepository(context: Context) {

    private var mTaskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)
    private val ncontext = context

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null

    }


    fun getList(userID: Int, taskFilter: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDatabaseHelper.readableDatabase
            //val sql = "SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME}"
            val sql = "SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userID AND ${DataBaseConstants.TASK.COLUMNS.COMPLETE} = $taskFilter "
            //cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userID AND ${DataBaseConstants.TASK.COLUMNS.COMPLETE} = $taskFilter ", null)
            cursor = db.rawQuery(sql,null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                    list.add(TaskEntity(id, userID, priorityId, description, dueDate, complete))
                }
            }else {
                println("------------> ${DataBaseConstants.TASK.TABLE_NAME} A query retornou 0!!!!")
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }

        return list
    }

    //get, update, insert, delete
    fun get(id: Int): TaskEntity? {

        var taskEntity: TaskEntity? = null

        try {
            val cursor: Cursor

            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.TASK.COLUMNS.ID, DataBaseConstants.TASK.COLUMNS.USERID, DataBaseConstants.TASK.COLUMNS.PRIORITYID, DataBaseConstants.TASK.COLUMNS.DESCRIPTION, DataBaseConstants.TASK.COLUMNS.DUEDATE, DataBaseConstants.TASK.COLUMNS.COMPLETE)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(id.toString())

            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val taskId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                // Preencho a entidade de usuario
                taskEntity = TaskEntity(id, userId, priorityId, description, dueDate, complete)

                println("------------> ${DataBaseConstants.TASK.TABLE_NAME} A query retornou ${cursor.count} VALORES")

            }else {
                println("------------> ${DataBaseConstants.TASK.TABLE_NAME} A query retornou 0!!!!")
            }


            cursor.close()
        } catch (e: Exception) {
            return taskEntity
            //throw e
        }

        return taskEntity
    }

    fun insert(task: TaskEntity) {
        // select, update, inserte, delete

        try {
            Toast.makeText(ncontext,"Tentando inserir",Toast.LENGTH_LONG).show()
            val db = mTaskDatabaseHelper.writableDatabase

            val complete: Int = if (task.complete) {
                1
            } else {
                0
            }

            val user : String
            if (DataBaseConstants.TASK.COLUMNS.USERID == null || DataBaseConstants.TASK.COLUMNS.USERID  == ""){
                 user = "1"
            }else{
                user = DataBaseConstants.TASK.COLUMNS.USERID
            }


            val insertValues = ContentValues()
            insertValues.put(user, task.userID)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityID)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues).toInt()

            println("----------------> o insert da task $insertValues")

        } catch (e: Exception) {
            throw e
        }
    }

    fun update(task: TaskEntity) {
        try {
            val db = mTaskDatabaseHelper.writableDatabase

            val complete: Int = if (task.complete) {1} else {0}


            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userID)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityID)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, selection, selectionArgs)

        } catch (e: Exception) {
            throw e
        }

    }

    fun delete(id: Int) {

        try {
            val db = mTaskDatabaseHelper.writableDatabase

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(id.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, selection, selectionArgs)


        } catch (e: Exception) {
            throw e
        }
    }
}