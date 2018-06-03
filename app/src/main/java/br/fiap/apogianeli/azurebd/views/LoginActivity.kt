package br.fiap.apogianeli.azurebd.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.business.UserBusiness
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private  lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //instanciar as entidades
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)


        setListeners()

        verifyLoggedUser()


    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.buttonLogin -> {
                handleLogin()
            }
            R.id.textRegister ->{
                startActivity(Intent(this,AdduserActivity::class.java))
            }

        }
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
    }

    private fun verifyLoggedUser(){
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)

        if (userId != "" && name != ""){
            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }

    }


    private fun handleLogin(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (mUserBusiness.login(email,password)){

            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }else{
            Toast.makeText(this,getString(R.string.user_pass_error),Toast.LENGTH_LONG).show()

        }
    }




}
