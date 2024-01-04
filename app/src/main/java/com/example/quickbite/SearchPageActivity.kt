package com.example.quickbite

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.quickbite.com.example.quickbite.util.AppViewModel
import com.example.quickbite.models.Restaurant

class SearchPageActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private val viewModel: AppViewModel by viewModels { SavedStateViewModelFactory(application, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navigationHandler = NavigationHandler(this)
        navigationHandler.setupNavigation(bottomNavigationView)

        viewModel.fetchRestaurants()

        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        setupRecyclerView(emptyList())

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        })
    }

    private fun setupRecyclerView(data: List<Restaurant>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SearchResultAdapter(data)
        recyclerView.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun performSearch(query: String) {
        viewModel.restaurants.observe(this) { restaurants ->
            val filteredRestaurants = restaurants.filter {
                it.restaurantName.contains(query, ignoreCase = true)
            }
            (recyclerView.adapter as? SearchResultAdapter)?.updateData(filteredRestaurants)

            recyclerView.visibility = if (filteredRestaurants.isEmpty()) View.GONE else View.VISIBLE
        }
    }



    /*private fun performSearch(query: String) {
        val filteredData = getDummyData().filter { it.contains(query, ignoreCase = true) }
        (recyclerView.adapter as? SearchResultAdapter)?.updateData(filteredData)

        recyclerView.visibility = if (filteredData.isEmpty()) View.GONE else View.VISIBLE
    }*/

    /*private fun performSearch(query: String) {
        viewModel.filterRestaurants(query)
    }
    private fun getDummyData(): List<String> {
        return listOf("Result 1", "Result 2", "Result 3", "Result 4", "Result 5")
    }*/
}
