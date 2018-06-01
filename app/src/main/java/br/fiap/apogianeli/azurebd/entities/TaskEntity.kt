package br.fiap.apogianeli.azurebd.entities

data class TaskEntity (
        val id: Int,
        val userid: Int,
        val priorityId: Int,
        val description: String,
        var dueDate: String,
        var complete: Boolean
)