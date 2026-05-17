package com.kashish_kirti.bespokethreads.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashish_kirti.bespokethreads.ui.components.ProductCard
import com.kashish_kirti.bespokethreads.ui.viewmodels.HomeViewModel
import com.kashish_kirti.bespokethreads.ui.viewmodels.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProduct: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedSort by viewModel.selectedSort.collectAsState()

    // UI state controllers
    var isSearchExpanded by remember { mutableStateOf(false) }
    var isSortMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearchExpanded) {
                        Text(text = "Bespoke Threads", fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    // Search Action Trigger
                    if (isSearchExpanded) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            placeholder = { Text("Search products, categories...") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                                unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    isSearchExpanded = false
                                    viewModel.updateSearchQuery("") // Reset search on close
                                }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Search")
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                        )
                    } else {
                        IconButton(onClick = { isSearchExpanded = true }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    }

                    // Sort Action Dropdown
                    if (!isSearchExpanded) {
                        Box {
                            IconButton(onClick = { isSortMenuExpanded = true }) {
                                Icon(imageVector = Icons.Default.List, contentDescription = "Sort")
                            }
                            DropdownMenu(
                                expanded = isSortMenuExpanded,
                                onDismissRequest = { isSortMenuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Default Sorting") },
                                    onClick = {
                                        viewModel.updateSortOption(SortOption.NONE)
                                        isSortMenuExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Price: Low to High") },
                                    onClick = {
                                        viewModel.updateSortOption(SortOption.PRICE_LOW_HIGH)
                                        isSortMenuExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Price: High to Low") },
                                    onClick = {
                                        viewModel.updateSortOption(SortOption.PRICE_HIGH_LOW)
                                        isSortMenuExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Name: A to Z") },
                                    onClick = {
                                        viewModel.updateSortOption(SortOption.NAME_A_Z)
                                        isSortMenuExpanded = false
                                    }
                                )
                            }
                        }

                        // Cart Action Icon
                        IconButton(onClick = onNavigateToCart) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = { onNavigateToProduct(product.id) }
                )
            }
        }
    }
}