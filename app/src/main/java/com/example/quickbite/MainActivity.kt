package com.example.quickbite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.quickbite.com.example.quickbite.util.AppViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels { SavedStateViewModelFactory(application, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.fetchRestaurants()
    }
}
