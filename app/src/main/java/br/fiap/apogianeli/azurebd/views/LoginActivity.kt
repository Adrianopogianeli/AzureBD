package br.fiap.apogianeli.azurebd.views

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.repository.UserRepository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setListeners()
        UserRepository.getInstance(this)

    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners(){
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave() {

    }


}
