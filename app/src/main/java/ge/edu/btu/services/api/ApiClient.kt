package ge.edu.btu.services.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://reqres.in/api/")
            .build()
    }

    fun usersService() = getRetrofitInstance().create(ApiService::class.java)
}
