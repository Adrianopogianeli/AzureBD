package br.fiap.apogianeli.azurebd.restapi

object ApiUtils {

    val BASE_URL = "your_url"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)

}
