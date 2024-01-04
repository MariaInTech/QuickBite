package com.example.quickbite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.R
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL
import com.example.quickbite.models.Restaurant

class RestaurantsAdapter(
    private val restaurantList: List<Restaurant>,
    private val context: Context,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_layout, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.restaurantName.text = restaurant.restaurantName
        holder.description.text = restaurant.description
        holder.restaurantImage.loadImageFromURL(restaurant.imageURL)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RestaurantPageActivity::class.java)
            intent.putExtra("selectedRestaurant", restaurant)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var restaurantImage: ImageView = itemView.findViewById(R.id.restaurantImage)
        var restaurantName: TextView = itemView.findViewById(R.id.restaurantName)
        var description: TextView = itemView.findViewById(R.id.description)
    }
}