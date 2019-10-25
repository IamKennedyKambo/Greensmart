package org.triniti.greensmart.data.network

import okhttp3.OkHttpClient
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.responses.AuthResponse
import org.triniti.greensmart.data.network.responses.BinsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface GreenApi {

    @Headers("Content-Type:application/json")
    @POST("users/login")
    suspend fun userLogin(
        @Body user: User
    ): Response<AuthResponse>

    @Headers("Content-Type:application/json")
    @POST("users")
    suspend fun userSignup(
        @Body user: User
    ): Response<AuthResponse>

    @GET("bins")
    suspend fun getBins(): Response<BinsResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): GreenApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://139.59.25.172:3000/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GreenApi::class.java)
        }
    }
}