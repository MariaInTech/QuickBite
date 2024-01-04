package com.example.quickbite

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.com.example.quickbite.util.AppViewModel
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL
import com.example.quickbite.models.MenuItem
import com.example.quickbite.models.Restaurant

class RestaurantPageActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels { SavedStateViewModelFactory(application, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_page)

        val selectedRestaurant = intent.getParcelableExtra<Restaurant>("selectedRestaurant")

        selectedRestaurant?.let {
            val restaurantImageView: ImageView = findViewById(R.id.restaurantImageView)
            val restaurantNameTextView: TextView = findViewById(R.id.restaurantNameTextView)
            val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
            val itemsRecyclerView: RecyclerView = findViewById(R.id.itemsRecyclerView)
            val backButton: Button = findViewById(R.id.goBackButton)

            restaurantImageView.loadImageFromURL(it.imageURL)
            restaurantNameTextView.text = it.restaurantName
            descriptionTextView.text = it.description

            Log.d("RestaurantPageActivity", "Menu Items for ${it.restaurantName}: ${it.menu}")

            val restaurantMenuFragment = RestaurantMenu()
            val bundle = Bundle().apply {
                putParcelableArrayList("menuItems", ArrayList(it.menu))
                putString("restaurantName", it.restaurantName)
            }

            restaurantMenuFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.restaurantMenuContainer, restaurantMenuFragment)
                .commit()

            // Set OnClickListener for the Back button
            backButton.setOnClickListener {
                navigateBack()
            }
        }
    }

    // Function to navigate back
    private fun navigateBack() {
        onBackPressed()
    }
}
