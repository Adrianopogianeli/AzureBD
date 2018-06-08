package br.fiap.apogianeli.azurebd.entities

import java.util.*

data class TaskEntity (val id: Int,
                       val userID: Int,
                       val priorityID: Int,
                       var description: String,
                       var dueDate: String,
                       var price: Int,
                       var complete: Boolean){



}