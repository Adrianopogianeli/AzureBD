package br.fiap.apogianeli.azurebd.entities

import android.provider.ContactsContract

data class UserEntity(val id: Int,
                      var name: String,
                      var email: String,
                      var password: String = ""
)