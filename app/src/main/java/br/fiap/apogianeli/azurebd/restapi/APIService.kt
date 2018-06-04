package br.fiap.apogianeli.azurebd.restapi

import br.fiap.apogianeli.azurebd.entities.DolarEntity
import retrofit2.Call
import retrofit2.http.*

interface APIService {

 //   @POST("register")
 //   @FormUrlEncoded
 //   fun registrationPost(@Field("email") email: String,
 //                        @Field("password") password: String): Call<DolarEntity>

    @GET("latest?access_key={key}&format=1")
    fun pesquisar(@Path("key")key: String): Call<DolarEntity>

}