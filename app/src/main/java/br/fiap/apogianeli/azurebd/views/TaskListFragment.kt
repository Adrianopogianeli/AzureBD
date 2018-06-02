package br.fiap.apogianeli.azurebd.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.adapter.TaskListAdapter
import br.fiap.apogianeli.azurebd.business.TaskBusiness
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.util.SecurityPreferences


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// private const val ARG_PARAM1 = "param1"
// private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TaskListFragment : Fragment(), View.OnClickListener {


    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): TaskListFragment {
/*
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
*/
            return TaskListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context

        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)

        // 1 Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 Definir um layout com os itens de listagem

        mRecyclerTaskList.adapter = TaskListAdapter(taskList = mutableListOf())

        // 3 Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume () {
        super.onResume()
        loadTasks()
    }


    override fun onClick(view: View) {
        when (view.id){
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
    }

    private fun loadTasks() {

        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        val taskList = mTaskBusiness.getList(userId)

        mRecyclerTaskList.adapter = TaskListAdapter(taskList)

    }

}