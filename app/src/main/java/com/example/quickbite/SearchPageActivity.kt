package com.example.quickbite

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.View

class SearchPageActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navigationHandler = NavigationHandler(this)
        navigationHandler.setupNavigation(bottomNavigationView)

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

    private fun setupRecyclerView(data: List<String>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SearchResultAdapter(data)
        recyclerView.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun performSearch(query: String) {
        val filteredData = getDummyData().filter { it.contains(query, ignoreCase = true) }
        (recyclerView.adapter as? SearchResultAdapter)?.updateData(filteredData)

        recyclerView.visibility = if (filteredData.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun getDummyData(): List<String> {
        return listOf("Result 1", "Result 2", "Result 3", "Result 4", "Result 5")
    }
}
