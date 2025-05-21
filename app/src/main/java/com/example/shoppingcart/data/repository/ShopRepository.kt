package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.model.product.ProductData
import com.example.shoppingcart.data.network.RetrofitInstance

class ShopRepository {

    private val api = RetrofitInstance.apiService

    suspend fun getAllProducts(): List<ProductData>? {
        val response = api.getAllProducts()
        return if (response.isSuccessful) response.body() else null
    }

}
