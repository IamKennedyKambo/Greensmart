package org.triniti.greensmart.data.network

import okhttp3.OkHttpClient
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.responses.AuthResponse
import org.triniti.greensmart.data.network.responses.BinsResponse
import org.triniti.greensmart.data.network.responses.ProductsResponse
import org.triniti.greensmart.data.network.responses.ShopsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

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

    @Headers("Content-Type:application/json")
    @PUT("users/{id}")
    suspend fun userUpdate(@Path("id") id: Int, @Body user: User): Response<AuthResponse>

    @GET("bins")
    suspend fun getBins(): Response<BinsResponse>

    @GET("shops")
    suspend fun getShops(): Response<ShopsResponse>

    @GET("catalog/{shopId}")
    suspend fun fetchItems(@Path("shopId") shopId: Int): Response<ProductsResponse>

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