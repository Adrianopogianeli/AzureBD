package br.fiap.apogianeli.azurebd.viewholder


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.Image
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.AlertDialogLayout
import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.entities.OnTaskListFragmentInteractionListener
import br.fiap.apogianeli.azurebd.entities.TaskEntity
import br.fiap.apogianeli.azurebd.repository.PriorityCacheConstants


class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteractionListener) : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)
    private val mPrice: TextView = itemView.findViewById(R.id.textSetPrice)


    fun bindData(task: TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityID)
        mTextDate.text = task.dueDate
        mPrice.text = "AU$ "+task.price.toString()

        if (task.complete) {
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        // evento de click p edit
/*        mTextDescription.setOnClickListener(View.OnClickListener {  })
            listener.onListclick(task.id)
    }*/
        mTextDescription.setOnClickListener({
            listener.onListclick(task.id)
        })

        mTextDescription.setOnLongClickListener({
            showConfirmationDialog(task)

            true
        })

        mImageTask.setOnClickListener({
            if (task.complete){
                listener.onUncompliteClick(task.id)
            }else{
                listener.onCompliteClick(task.id)
            }
        })

    }

    private fun showConfirmationDialog(task: TaskEntity){
        //listener.onDeleteClick(taskId)
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.remocao_do_produto))
                .setMessage("Are you sure you want to remove ${task.description}?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton(context.getString(R.string.Remove),handleRemoval(listener,task.id))
                .setNegativeButton("Cancel",null).show()

    }

    // implemtent class de interface para delete
    private class handleRemoval(val listener: OnTaskListFragmentInteractionListener, val taskId: Int) : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener.onDeleteClick(taskId)
        }

    }


}