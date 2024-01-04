package com.example.quickbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbite.models.MenuItem
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL

class AddToCart : AppCompatActivity() {
    private lateinit var btnAddToCart: Button
    private lateinit var itemImageView: ImageView
    private lateinit var itemNameTextView: TextView
    private lateinit var itemPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)


        btnAddToCart = findViewById(R.id.btnAddToCart)
        itemImageView = findViewById(R.id.itemImageView)
        itemNameTextView = findViewById(R.id.itemNameTextView)
        itemPriceTextView = findViewById(R.id.itemPriceTextView)

        val selectedItem: MenuItem? = intent.getParcelableExtra("selectedItem")
        val restaurantName: String? = intent.getStringExtra("restaurantName")

        val selectedItemImageURL = selectedItem?.imageURL ?: ""
        val selectedItemName = selectedItem?.itemName ?: "Item Name Not Available"
        val selectedItemPrice = selectedItem?.price ?: 0.0

        // Display item details
        itemImageView.loadImageFromURL(selectedItemImageURL)
        itemNameTextView.text = selectedItemName
        itemPriceTextView.text = "Price: $$selectedItemPrice"

        btnAddToCart.setOnClickListener {
            val cartIntent = Intent(this@AddToCart, CartActivity::class.java)
            cartIntent.putExtra("selectedItem", selectedItem)
            cartIntent.putExtra("restaurantName", restaurantName)
            startActivity(cartIntent)
        }
    }


}
