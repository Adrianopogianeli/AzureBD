package br.fiap.apogianeli.azurebd.business

import android.content.Context
import br.fiap.apogianeli.azurebd.R
import br.fiap.apogianeli.azurebd.constants.TaskConstants
import br.fiap.apogianeli.azurebd.entities.UserEntity
import br.fiap.apogianeli.azurebd.repository.UserRepository
import br.fiap.apogianeli.azurebd.util.SecurityPreferences
import br.fiap.apogianeli.azurebd.util.ValidationException

class UserBusiness (val context: Context) {

    private val mUserRepository : UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences : SecurityPreferences = SecurityPreferences(context)

    fun login (email: String, password: String) : Boolean {

        val user: UserEntity? = mUserRepository.get(email,password)
        if ( user != null){
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            return true

        }else{

            return false
        }




    }

    fun insert (name: String, email: String, password: String){

        // Valida user
        try {

            if (name == "" || email == "" || password == ""){
                throw ValidationException(context.getString(R.string.info_all_fields))
            }

            if (mUserRepository.isEmailExistent(email)){
                throw ValidationException(context.getString(R.string.email_exists))
            }

            val userId =  mUserRepository.insert(name,email,password)

            // Salve user info on shared
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)


        }
        catch (e: Exception){
            throw e
        }


    }



}