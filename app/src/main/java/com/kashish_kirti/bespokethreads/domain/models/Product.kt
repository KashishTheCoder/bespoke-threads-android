package com.kashish_kirti.bespokethreads.domain.models

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val artisanName: String, // Who made the custom thread
    val category: String,
    val imageUrl: String // We'll use placeholder URLs for now
)