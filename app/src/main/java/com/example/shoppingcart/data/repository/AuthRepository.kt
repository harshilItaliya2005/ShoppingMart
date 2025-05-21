package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.model.user.UserLoginRequest
import com.example.shoppingcart.data.model.user.UserSignupRequest
import com.example.shoppingcart.data.network.RetrofitInstance

class AuthRepository {
    private val api = RetrofitInstance.apiService

    suspend fun login(username: String, password: String) =
        api.loginUser(UserLoginRequest(username, password))

    suspend fun signUp(request: UserSignupRequest) =
        api.signUpUser(request)
}
