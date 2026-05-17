package com.kashish_kirti.bespokethreads.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kashish_kirti.bespokethreads.data.repository.ProductRepository
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    fun loadProduct(productId: String) {
        _product.value = repository.getProductById(productId)
    }
}