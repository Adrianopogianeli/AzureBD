package br.fiap.apogianeli.azurebd.entities

interface OnTaskListFragmentInteractionListener {

    fun onListclick(taskId: Int)

    fun onDeleteClick(taskId: Int)

    fun onUncompliteClick(taskId: Int)

    fun onCompliteClick(taskId: Int)



}