package com.example.quickbite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.CartItem
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.quickbite.com.example.quickbite.Db_handler.CartItemDbHelper
import com.example.quickbite.models.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CartActivity : AppCompatActivity(), CartAdapter.CartItemClickListener {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cartItemsList = mutableListOf<CartItem>()


    private lateinit var cartItemDbHelper: CartItemDbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        cartAdapter = CartAdapter(cartItemsList, this)
        recyclerView.adapter = cartAdapter

        cartItemDbHelper = CartItemDbHelper(this)

        loadCartItemsFromDatabase()

        val selectedItem: MenuItem? = intent.getParcelableExtra("selectedItem")
        val restaurantName: String? = intent.getStringExtra("restaurantName")

        Log.d("CartActivity", "Selected Item Name: ${selectedItem?.itemName}")
        Log.d("CartActivity", "Restaurant Name: $restaurantName")


        val cartItem = CartItem(
            itemId = UUID.randomUUID().toString(),
            restaurantName = restaurantName ?: "Restaurant Not Available",
            itemName = selectedItem?.itemName ?: "Item Not Available",
            quantity = 1,
            price = selectedItem?.price ?: 0.0,
            imageResourceURL = selectedItem?.imageURL ?: ""
        )

        if (cartItem.restaurantName != "Restaurant Not Available" && cartItem.itemName!="Item Not Available") {
            saveCartItem(cartItem)
        }


        for ((index, cartItem) in cartItemsList.withIndex()) {
            Log.d("CartItem", "Item $index: Restaurant=${cartItem.restaurantName}, ItemName=${cartItem.itemName}, Quantity=${cartItem.quantity}, Price=${cartItem.price}, ImageURL=${cartItem.imageResourceURL}")
        }

        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            addOrder()
            calculateTotalPrice()
        }
    }

    private fun loadCartItemsFromDatabase() {
        val db = cartItemDbHelper.readableDatabase

        val projection = arrayOf(
            CartItemDbHelper.COLUMN_ITEM_ID,
            CartItemDbHelper.COLUMN_RESTAURANT_NAME,
            CartItemDbHelper.COLUMN_ITEM_NAME,
            CartItemDbHelper.COLUMN_QUANTITY,
            CartItemDbHelper.COLUMN_PRICE,
            CartItemDbHelper.COLUMN_IMAGE_URL
        )

        val cursor = db.query(
            CartItemDbHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_ITEM_ID))
                val restaurantName = getString(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_RESTAURANT_NAME))
                val itemName = getString(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_ITEM_NAME))
                val quantity = getInt(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_QUANTITY))
                val price = getDouble(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_PRICE))
                val imageURL = getString(getColumnIndexOrThrow(CartItemDbHelper.COLUMN_IMAGE_URL))

                val cartItem = CartItem(itemId, restaurantName, itemName, quantity, price, imageURL)
                cartItemsList.add(cartItem)
            }
        }

        cartAdapter.notifyDataSetChanged()

        cursor.close()
    }

    private fun addCartItemToDatabase(cartItem: CartItem) {
        val db = cartItemDbHelper.writableDatabase

        val values = ContentValues().apply {
            put(CartItemDbHelper.COLUMN_ITEM_ID, cartItem.itemId)
            put(CartItemDbHelper.COLUMN_RESTAURANT_NAME, cartItem.restaurantName)
            put(CartItemDbHelper.COLUMN_ITEM_NAME, cartItem.itemName)
            put(CartItemDbHelper.COLUMN_QUANTITY, cartItem.quantity)
            put(CartItemDbHelper.COLUMN_PRICE, cartItem.price)
            put(CartItemDbHelper.COLUMN_IMAGE_URL, cartItem.imageResourceURL)
        }

        val newRowId = db?.insert(CartItemDbHelper.TABLE_NAME, null, values)
        Log.d("CartActivity", "Inserted new row with ID: $newRowId")
    }

    private fun saveCartItem(cartItem: CartItem) {
        addCartItemToDatabase(cartItem)
        cartItemsList.add(cartItem)
        cartAdapter.notifyDataSetChanged()
    }

    private fun addOrder() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val ordersCollection = firestore.collection("orders")

            val orderData = mapOf(
                "user" to firestore.document("users/$userId"),

                "items" to cartItemsList.map { cartItem ->
                    mapOf(
                        "restaurantName" to cartItem.restaurantName,
                        "itemId" to cartItem.itemId,
                        "itemName" to cartItem.itemName,
                        "itemQuanity" to cartItem.quantity,
                        "price" to cartItem.price
                    )
                },
                "orderDate" to FieldValue.serverTimestamp()
            )

            ordersCollection.add(orderData)
                .addOnSuccessListener {
                    Log.d("Firestore", "Order added successfully with ID: ${it.id}")

                    // After adding items to Firebase, delete all items from the local database
                    cartItemDbHelper.deleteAllItems()

                    // Clear the local cartItemsList
                    cartItemsList.clear()

                    cartAdapter.notifyDataSetChanged()

                    Log.d("CartActivity", "All items removed from the local database after adding to Firebase")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error adding order", e)
                }
        } else {
            Log.e("Firestore", "User not authenticated")
        }
    }


    private fun addRandomOrders() {
        val userId = auth.currentUser?.uid

        if (userId != null) {

            val ordersCollection = firestore.collection("orders")

            val orderData = listOf(
                createRandomOrder("Tasty Delights"),
                createRandomOrder("Spicy Bites"),
                createRandomOrder("Sushi Haven")
            )

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

        return mapOf(
            "user" to firestore.document("users/${auth.currentUser?.uid}"), // Reference to the user
            "restaurantName" to restaurantName,
            "items" to items,
            "orderDate" to FieldValue.serverTimestamp()
        )
    }

    private fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        for (cartItem in cartItemsList) {
            totalPrice += cartItem.price * cartItem.quantity
        }
        return totalPrice
    }

    override fun onQuantityChange(position: Int, newQuantity: Int) {

    }

    override fun updateTotalPrice(totalPrice: Double) {
    }

}
