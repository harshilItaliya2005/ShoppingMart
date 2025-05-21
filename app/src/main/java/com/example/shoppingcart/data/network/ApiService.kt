package com.example.shoppingcart.data.network

import com.example.shoppingcart.data.model.product.ProductData
import com.example.shoppingcart.data.model.user.UserLoginRequest
import com.example.shoppingcart.data.model.user.UserLoginResponse
import com.example.shoppingcart.data.model.user.UserSignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun loginUser(@Body request: UserLoginRequest): Response<UserLoginResponse>

    @POST("users")
    suspend fun signUpUser(@Body request: UserSignupRequest): Response<UserSignupRequest>

    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductData>>

}
