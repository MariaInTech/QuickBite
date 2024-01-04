package com.example.quickbite.models

data class CartItem(
    val itemId: String,
    val restaurantName: String,
    val itemName: String,
    val quantity: Int,
    val price: Double,
    val imageResourceURL: String
)
