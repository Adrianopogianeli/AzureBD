package br.fiap.apogianeli.azurebd.business

import android.content.Context
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.entities.TaskEntity
import br.fiap.apogianeli.azurebd.repository.TaskRepository
import br.fiap.apogianeli.azurebd.util.SecurityPreferences

class TaskBusiness (context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mTaskRepository.get(id)

    fun getList(taskFilter: Int) : MutableList<TaskEntity> {
        val userID = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userID, taskFilter)

    }
    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)

    fun update(taskEntity: TaskEntity) = mTaskRepository.update(taskEntity)

    fun delete(taskId: Int) = mTaskRepository.delete(taskId)

    fun complete(taskId: Int, complete: Boolean){
        val task = mTaskRepository.get(taskId)
        if (task  != null){
            task.complete = complete
            mTaskRepository.update(task)
        }
    }

}