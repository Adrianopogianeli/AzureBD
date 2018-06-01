package br.fiap.apogianeli.azurebd.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.business.PriorityBusiness
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*


class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {



    private lateinit var mPriorityBusiness: PriorityBusiness
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

    mPriorityBusiness = PriorityBusiness(this)

        setListeners()

        loadPriorities()

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonDate ->{
                openDatePickerDialog()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {

        val calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        mSimpleDateFormat.format(calendar.time)
        buttonDate.text = mSimpleDateFormat.format(calendar.time)

    }

    private fun openDatePickerDialog(){

        val dt = Calendar.getInstance()
        val year = dt.get(Calendar.YEAR)
        val month = dt.get(Calendar.MONTH)
        val dayOfMonth = dt.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,this, year,month,dayOfMonth).show()
    }

    private fun setListeners(){
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun loadPriorities() {

        val lstPriorityEntity = mPriorityBusiness.getList()

        val lstPriorities = lstPriorityEntity.map { it.description }

        print(lstPriorities)

        val adapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinnerPriority.adapter = adapter

    }


}
