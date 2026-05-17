package com.kashish_kirti.bespokethreads.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kashish_kirti.bespokethreads.data.repository.ProductRepository
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // In a real app, this would be injected via Dagger/Hilt
    private val repository = ProductRepository()

    // Using StateFlow to hold the list of products for Compose to observe
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _products.value = repository.getProducts()
        }
    }
}