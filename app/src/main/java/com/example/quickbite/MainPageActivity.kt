package com.example.quickbite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.quickbite.com.example.quickbite.util.AppViewModel
import androidx.appcompat.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPageActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels { SavedStateViewModelFactory(application, this) }

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationHandler = NavigationHandler(this)
        navigationHandler.setupNavigation(bottomNavigationView)


        /*val searchCardView = findViewById(R.id.searchCardView)
        val searchView = searchCardView.findViewById(R.id.searchView)

        // Set an OnClickListener for the SearchView
        searchView.setOnClickListener {
            // Start the SearchActivity
            val intent = Intent(this, SearchPageActivity::class.java)
            startActivity(intent)
        }*/

        //viewModel.fetchRestaurants()

    }
}
