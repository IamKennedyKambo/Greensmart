package org.triniti.greensmart.data.network

import org.triniti.greensmart.data.network.responses.AuthResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(@Field("email") email: String, @Field("password") password: String): Response<AuthResponse>

    companion object {
        operator fun invoke(): LoginApi {
            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoginApi::class.java)
        }
    }
}