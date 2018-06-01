package br.fiap.apogianeli.azurebd.business

import android.content.Context
import br.fiap.apogianeli.azurebd.entities.TaskEntity
import br.fiap.apogianeli.azurebd.repository.TaskRepository

class TaskBusiness(context: Context) {


    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int) : MutableList<TaskEntity> = mTaskRepository.getList(userId)


}