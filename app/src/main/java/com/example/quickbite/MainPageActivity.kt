package com.example.quickbite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.quickbite.com.example.quickbite.util.AppViewModel
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

        viewModel.fetchRestaurants()

    }
}
