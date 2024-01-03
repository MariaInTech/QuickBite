package com.example.quickbite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.CartItem
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class CartActivity : AppCompatActivity(), CartAdapter.CartItemClickListener {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //addRandomOrders()

        val cartItems = generateDummyCartItems()

        recyclerView = findViewById(R.id.recyclerView)
        cartAdapter = CartAdapter(cartItems, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter
    }

    private fun addRandomOrders() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Reference to the Orders collection
            val ordersCollection = firestore.collection("orders")

            // Dummy data for orders
            val orderData = listOf(
                createRandomOrder("Tasty Delights"),
                createRandomOrder("Spicy Bites"),
                createRandomOrder("Sushi Haven")
                // Add more random orders as needed
            )

            // Add each order to Firestore
            for (order in orderData) {
                ordersCollection.add(order)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Order added successfully with ID: ${it.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error adding order", e)
                    }
            }
        } else {
            Log.e("Firestore", "User not authenticated")
        }
    }

    private fun createRandomOrder(restaurantName: String): Map<String, Any> {
        val random = Random()
        val items = listOf(
            mapOf("itemId" to "101", "itemName" to "Classic Burger", "price" to 10.99),
            mapOf("itemId" to "102", "itemName" to "Margherita Pizza", "price" to 12.99)
        )

        // Create a random order document
        return mapOf(
            "user" to firestore.document("users/${auth.currentUser?.uid}"), // Reference to the user
            "restaurantName" to restaurantName,
            "items" to items,
            "orderDate" to FieldValue.serverTimestamp()
        )
    }
    private fun generateDummyCartItems(): List<CartItem> {
        // Replace this with your actual data source
        return listOf(
            CartItem("Item 1", 2, 10.99, ""),
            CartItem("Item 2", 1, 5.99, ""),
            CartItem("Item 3", 3, 15.99, "")
        )
    }

    override fun onQuantityChange(position: Int, newQuantity: Int) {
        /*val updatedItems = cartAdapter.currentList.toMutableList()
        updatedItems[position] = updatedItems[position].copy(quantity = newQuantity)
        cartAdapter.submitList(updatedItems)*/
    }
}
