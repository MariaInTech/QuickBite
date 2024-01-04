package com.example.quickbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbite.models.MenuItem

class AddToCart : AppCompatActivity() {
    private lateinit var btnAddToCart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        btnAddToCart = findViewById(R.id.btnAddToCart)

        val selectedItem: MenuItem? = intent.getParcelableExtra("selectedItem")
        val restaurantName: String? = intent.getStringExtra("restaurantName")

        val selectedItemName = selectedItem?.itemName ?: "Item Name Not Available"
        val restaurantNameText = restaurantName ?: "Restaurant Name Not Available"
        btnAddToCart.setOnClickListener {
            val cartIntent = Intent(this@AddToCart, CartActivity::class.java)

            cartIntent.putExtra("selectedItem", selectedItem)
            cartIntent.putExtra("restaurantName", restaurantName)

            startActivity(cartIntent)
        }


    }
}