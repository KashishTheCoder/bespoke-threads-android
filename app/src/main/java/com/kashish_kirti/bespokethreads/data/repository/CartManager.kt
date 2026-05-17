package com.kashish_kirti.bespokethreads.data.repository

import com.kashish_kirti.bespokethreads.domain.models.CartItem
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartManager {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Modified to accept an explicit initial quantity
    fun addToCart(product: Product, quantity: Int = 1, notes: String = "") {
        val currentList = _cartItems.value.toMutableList()
        val existingItemIndex = currentList.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex != -1) {
            val existing = currentList[existingItemIndex]
            currentList[existingItemIndex] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            currentList.add(CartItem(product = product, quantity = quantity, customizationNotes = notes))
        }
        _cartItems.value = currentList
    }

    // NEW: Updates quantity explicitly from the Cart Screen
    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }
        val currentList = _cartItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            currentList[index] = currentList[index].copy(quantity = newQuantity)
            _cartItems.value = currentList
        }
    }

    // NEW: Deletes an item completely from the cart
    fun removeFromCart(productId: String) {
        val currentList = _cartItems.value.toMutableList()
        currentList.removeAll { it.product.id == productId }
        _cartItems.value = currentList
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}