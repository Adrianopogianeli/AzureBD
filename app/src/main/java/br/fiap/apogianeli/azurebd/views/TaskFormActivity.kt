package br.fiap.apogianeli.azurebd.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.business.PriorityBusiness
import br.fiap.apogianeli.azurebd.business.TaskBusiness
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.entities.PriorityEntity
import br.fiap.apogianeli.azurebd.entities.TaskEntity
import br.fiap.apogianeli.azurebd.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*


class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    private var mLstPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mLstPrioritiesId: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        setListeners()

        loadPriorities()


    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonDate -> {
                openDatePrickerDialog()
            }
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun setListeners(){
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave(){

        try{
            //  [1, 2, 3, 4]
            val priorityId = mLstPrioritiesId[spinnerPriority.selectedItemPosition]
            val complete = checkboxComplete.isChecked
            val dueDate = buttonDate.text.toString()
            val description = editDescription.text.toString()
            val userID =  mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

            val taskEntity = TaskEntity(0, userID, priorityId, description, dueDate, complete )
            mTaskBusiness.insert(taskEntity)

            Toast.makeText(this,"Task adicionada ao DB !!! viva Dilma", Toast.LENGTH_LONG).show()

        }catch (e: Exception){
            Toast.makeText(this, getString(R.string.general_erro), Toast.LENGTH_LONG).show()
        }

    }

    private fun openDatePrickerDialog() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)


        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

    private fun loadPriorities(){
        mLstPrioritiesEntity = mPriorityBusiness.getList()
        val lstPriorities = mLstPrioritiesEntity.map { it.description}
        mLstPrioritiesId = mLstPrioritiesEntity.map {it.id}.toMutableList()

        // [Baixa, media, alta, Critica]
        //  [1, 2, 3, 4]

        val adapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinnerPriority.adapter = adapter
    }

}
