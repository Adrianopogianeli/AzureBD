package br.fiap.apogianeli.azurebd.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.entities.OnTaskListFragmentInteractionListener
import br.fiap.apogianeli.azurebd.entities.TaskEntity
import br.fiap.apogianeli.azurebd.viewholder.TaskViewHolder

class TaskListAdapter (val taskList: List<TaskEntity>, val listener: OnTaskListFragmentInteractionListener) : RecyclerView.Adapter <TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)


        return TaskViewHolder(view, context ,listener)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)
    }
}