package org.triniti.greensmart.data.network

import okhttp3.OkHttpClient
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.responses.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GreenApi {

    @Headers("Content-Type:application/json", "Connection:Close")
    @POST("users/login")
    suspend fun userLogin(
        @Body user: User
    ): Response<AuthResponse>

    @Headers("Content-Type:application/json")
    @POST("users")
    suspend fun userSignup(
        @Body user: User
    ): Response<AuthResponse>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
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

    @POST("carts/{userId}")
    suspend fun getCart(@Path("userId") userId: Int): Response<CartResponse>

    @Headers("Content-Type:application/json")
    @POST("cart")
    suspend fun createEntry(@Body cart: Cart): Response<CartResponse>

    @POST("cart/{cartId}")
    suspend fun deleteEntry(@Path("cartId") id: Int)

    @GET("news")
    suspend fun getNews(): Response<NewsResponse>

    companion object {
        operator fun invoke(interceptor: NetworkConnectionInterceptor): GreenApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://139.59.25.172:3000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GreenApi::class.java)
        }
    }
}