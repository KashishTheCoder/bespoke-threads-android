package com.kashish_kirti.bespokethreads.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.tasks.await

class ProductRepository {

    // Initialize Firestore
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    // Suspend function because network calls take time
    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productsCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                // Manually mapping the document data to our Product data class
                Product(
                    id = document.id, // Using the Firestore Document ID
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: "",
                    price = document.getDouble("price") ?: 0.0,
                    artisanName = document.getString("artisanName") ?: "Unknown Artisan",
                    category = document.getString("category") ?: "Uncategorized",
                    imageUrl = document.getString("imageUrl") ?: "https://picsum.photos/400/400"
                )
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching products", e)
            emptyList() // Return empty list if network fails
        }
    }

    suspend fun getProductById(id: String): Product? {
        return try {
            val document = productsCollection.document(id).get().await()
            if (document.exists()) {
                Product(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: "",
                    price = document.getDouble("price") ?: 0.0,
                    artisanName = document.getString("artisanName") ?: "Unknown Artisan",
                    category = document.getString("category") ?: "Uncategorized",
                    imageUrl = document.getString("imageUrl") ?: "https://picsum.photos/400/400"
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching product details", e)
            null
        }
    }
}