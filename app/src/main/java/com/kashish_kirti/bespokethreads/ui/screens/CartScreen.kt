package com.kashish_kirti.bespokethreads.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kashish_kirti.bespokethreads.data.repository.CartManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cartItems by CartManager.cartItems.collectAsState()
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Custom Bag") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                BottomAppBar {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total: Rs. $totalPrice",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = onNavigateToCheckout) {
                            Text("Checkout")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your bag is empty.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems, key = { it.product.id }) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.product.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text("Rs. ${item.product.price} each", style = MaterialTheme.typography.bodySmall)
                                }

                                // Total price per variant row
                                Text("Rs. ${item.product.price * item.quantity}", fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Dynamic inline stepper modifiers
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    FilledIconButton(
                                        onClick = { CartManager.updateQuantity(item.product.id, item.quantity - 1) },
                                        modifier = Modifier.size(32.dp),
                                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                    ) {
                                        Text("-", color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold)
                                    }

                                    Text(
                                        text = item.quantity.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )

                                    FilledIconButton(
                                        onClick = { CartManager.updateQuantity(item.product.id, item.quantity + 1) },
                                        modifier = Modifier.size(32.dp),
                                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                    ) {
                                        Text("+", color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold)
                                    }
                                }

                                // Delete button element
                                IconButton(onClick = { CartManager.removeFromCart(item.product.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove Item",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}