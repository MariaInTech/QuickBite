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
    /*fun fetchMenuItems(restaurantId: Int, callback: (List<MenuItem>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.getMenuItems(restaurantId)

                if (response.isSuccessful) {
                    val menuItems = response.body()
                    if (menuItems != null) {
                        callback.invoke(menuItems)
                    } else {
                        Log.e(TAG, "Menu items response body is null")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
            }
        }
    }*/
}

