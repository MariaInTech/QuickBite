package com.example.quickbite.com.example.quickbite.util

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.ApiClient
import com.example.quickbite.models.MenuItem
import com.example.quickbite.models.Restaurant
import kotlinx.coroutines.launch

class AppViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private var originalRestaurants: List<Restaurant>? = null
    fun fetchRestaurants() {
        viewModelScope.launch {
            try {
                val response = ApiClient.getAllRestaurants()

                if (response.isSuccessful) {
                    val restaurants = response.body()
                    _restaurants.value = restaurants
                } else {
                    Log.e(TAG, "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
            }
        }
    }
    fun filterRestaurants(query: String) {
        originalRestaurants?.let { restaurants ->
            val filteredRestaurants = restaurants.filter {
                it.restaurantName.contains(query, ignoreCase = true)
            }
            _restaurants.value = filteredRestaurants
        }
    }
}

