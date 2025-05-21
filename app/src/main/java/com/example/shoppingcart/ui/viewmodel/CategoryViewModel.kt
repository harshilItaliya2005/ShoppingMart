package com.example.shoppingcart.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.model.product.ProductData
import com.example.shoppingcart.data.repository.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: ShopRepository): ViewModel() {
    private val _products = MutableStateFlow<List<ProductData>>(emptyList())
    val products: StateFlow<List<ProductData>> =_products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val productList = repository.getAllProducts()
                if (productList != null) {
                    _products.value = productList
                    Log.d("ShopViewModel", "Products fetched: $productList")
                } else {
                    _errorMessage.value = "No products found"
                    Log.e("ShopViewModel", "No products found")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch products: ${e.message}"
                Log.e("ShopViewModel", "Error: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
class CategoryViewModelFactory(private val repository: ShopRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}