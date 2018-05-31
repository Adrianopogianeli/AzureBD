package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.business.UserBusiness
import br.fiap.apogianeli.azurebd.util.ValidationException
import kotlinx.android.synthetic.main.activity_adduser.*

class AdduserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness : UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adduser)

        // Eventos
        setListeners()

        // Instanciar variaveis da classe
        mUserBusiness = UserBusiness(this)
        //UserRepository.getInstance(this)

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

        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            // faz a inseercao do user
            mUserBusiness.insert(name, email, password)

            // Vai para menu e nao permite q user retorne
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        catch (e: ValidationException){
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this,getString(R.string.general_erro),Toast.LENGTH_LONG).show()
        }


    }


}
