package com.example.quickbite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.MenuItem
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL

class MenuItemsAdapter(
    private val context: Context,
    private val items: List<MenuItem>,
    private val restaurantName: String?
) : RecyclerView.Adapter<MenuItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.itemName ?: "Item Name Not Available"
        holder.itemPrice.text = "Price: $${item.price}"
        item.imageURL?.let {
            holder.itemImage.loadImageFromURL(it)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddToCart::class.java)
            intent.putExtra("selectedItem", item)
            intent.putExtra("restaurantName",restaurantName)
            context.startActivity(intent)
        }

        holder.goBackButton.setOnClickListener {
            // Call a method in your RestaurantPage activity to navigate back
            // You can use an interface/callback or other mechanisms for communication
            (context as? RestaurantPageActivity)?.navigateBack()
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
        var itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        var goBackButton: Button = itemView.findViewById(R.id.goBackButton)
    }
}