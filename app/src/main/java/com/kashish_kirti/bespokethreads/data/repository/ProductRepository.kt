package com.kashish_kirti.bespokethreads.data.repository

import com.kashish_kirti.bespokethreads.domain.models.Product

class ProductRepository {

    // Simulating fetching a list of products from a server
    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "1",
                title = "Hand-Embroidered Denim Jacket",
                description = "Custom vintage denim with floral back embroidery.",
                price = 4500.0,
                artisanName = "Kirti Kour",
                category = "Jackets",
                imageUrl = "https://picsum.photos/seed/jacket/400/400"
            ),
            Product(
                id = "2",
                title = "Bespoke Linen Kurta",
                description = "Breathable summer linen tailored to your exact measurements.",
                price = 2800.0,
                artisanName = "Kashish Basreja",
                category = "Traditional",
                imageUrl = "https://picsum.photos/seed/kurta/400/400"
            ),
            Product(
                id = "3",
                title = "Crochet Winter Beanie",
                description = "Chunky yarn handmade beanie. Available in custom colors.",
                price = 1200.0,
                artisanName = "Your Wish Crochet",
                category = "Accessories",
                imageUrl = "https://picsum.photos/seed/beanie/400/400"
            ),
            Product(
                id = "4",
                title = "Tailored Silk Blouse",
                description = "Elegant pure silk blouse with custom sleeve lengths.",
                price = 3500.0,
                artisanName = "Thread & Needle Co.",
                category = "Tops",
                imageUrl = "https://picsum.photos/seed/blouse/400/400"
            )
        )
    }
}