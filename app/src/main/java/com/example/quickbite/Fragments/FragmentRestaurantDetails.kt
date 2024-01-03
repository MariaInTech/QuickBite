package com.example.quickbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL
import com.example.quickbite.models.Restaurant

class FragmentRestaurantDetails : Fragment() {

    private lateinit var restaurant: Restaurant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaurant_details, container, false)

        // Access restaurant data and update UI
        val restaurantNameTextView: TextView = view.findViewById(R.id.restaurantNameTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val restaurantImageView: ImageView = view.findViewById(R.id.restaurantImageView)

        val restaurantName = arguments?.getString("restaurantName") ?: "Default Restaurant Name"
        val description = arguments?.getString("description") ?: "Default Description"
        val imageURL = arguments?.getString("imageURL")

        restaurantNameTextView.text = restaurantName
        descriptionTextView.text = description

        if (!imageURL.isNullOrEmpty()) {
            restaurantImageView.loadImageFromURL(imageURL)
        } else {
            restaurantImageView.setImageResource(R.drawable.mcdo)
        }

        return view
    }

    companion object {
        fun newInstance(restaurant: Restaurant): FragmentRestaurantDetails {
            return FragmentRestaurantDetails().apply {
                arguments = Bundle().apply {
                    putString("restaurantName", restaurant.restaurantName)
                    putString("description", restaurant.description)
                    putString("imageURL", restaurant.imageURL)
                }
            }
        }
    }
}
