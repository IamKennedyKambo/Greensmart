package org.triniti.greensmart.data.network

import okhttp3.OkHttpClient
import org.triniti.greensmart.data.network.responses.AuthResponse
import org.triniti.greensmart.data.network.responses.BinsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface GreenApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(@Field("email") email: String, @Field("password") password: String): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("bins")
    suspend fun getBins(): Response<BinsResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): GreenApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("localhost:3000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GreenApi::class.java)
        }
    }
}