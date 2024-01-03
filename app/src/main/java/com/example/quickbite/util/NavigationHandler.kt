package com.example.quickbite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationHandler(private val activity: AppCompatActivity) {

    fun setupNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (activity is MainPageActivity) {
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        startNewActivity(MainPageActivity::class.java)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_search -> {
                    if (activity is SearchPageActivity) {
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        startNewActivity(SearchPageActivity::class.java)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_cart -> {
                    if (activity is CartActivity) {
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        startNewActivity(CartActivity::class.java)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_user -> {
                    if (activity is UserProfileActivity) {
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        startNewActivity(UserProfileActivity::class.java)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun startNewActivity(destinationActivity: Class<*>) {
        val intent = Intent(activity, destinationActivity)
        activity.startActivity(intent)
    }
}