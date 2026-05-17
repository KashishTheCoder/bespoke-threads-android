package com.kashish_kirti.bespokethreads.domain.models

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val customizationNotes: String = "" // Perfect for a bespoke app!
)