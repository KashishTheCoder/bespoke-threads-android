package com.kashish_kirti.bespokethreads.data.repository

import com.kashish_kirti.bespokethreads.domain.models.CartItem
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartManager {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product, notes: String = "") {
        val currentList = _cartItems.value.toMutableList()
        // Check if item already exists to update quantity, or add new
        val existingItemIndex = currentList.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex != -1) {
            val existing = currentList[existingItemIndex]
            currentList[existingItemIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            currentList.add(CartItem(product = product, customizationNotes = notes))
        }
        _cartItems.value = currentList
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}