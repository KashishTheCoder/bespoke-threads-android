package com.kashish_kirti.bespokethreads.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashish_kirti.bespokethreads.data.repository.ProductRepository
import com.kashish_kirti.bespokethreads.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption {
    NONE, PRICE_LOW_HIGH, PRICE_HIGH_LOW, NAME_A_Z
}

class HomeViewModel : ViewModel() {

    private val repository = ProductRepository()

    // 1. Raw products from Firebase
    private val _rawProducts = MutableStateFlow<List<Product>>(emptyList())

    // 2. Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // 3. Sort option state
    private val _selectedSort = MutableStateFlow(SortOption.NONE)
    val selectedSort: StateFlow<SortOption> = _selectedSort

    // 4. Actively combined state: dynamically filters and sorts the raw list whenever query or sort changes
    val products: StateFlow<List<Product>> = combine(
        _rawProducts, _searchQuery, _selectedSort
    ) { products, query, sort ->
        // First, apply search filter
        val filtered = if (query.isBlank()) {
            products
        } else {
            products.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.artisanName.contains(query, ignoreCase = true)
            }
        }

        // Then, apply sorting mechanism
        when (sort) {
            SortOption.NONE -> filtered
            SortOption.PRICE_LOW_HIGH -> filtered.sortedBy { it.price }
            SortOption.PRICE_HIGH_LOW -> filtered.sortedByDescending { it.price }
            SortOption.NAME_A_Z -> filtered.sortedBy { it.title }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _rawProducts.value = repository.getProducts()
        }
    }

    // Public functions for UI interactions
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSortOption(option: SortOption) {
        _selectedSort.value = option
    }
}