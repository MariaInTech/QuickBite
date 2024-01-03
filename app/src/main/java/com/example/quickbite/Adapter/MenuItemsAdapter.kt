package com.example.quickbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.MenuItem
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL

class MenuItemsAdapter(private val items: List<MenuItem>) :
    RecyclerView.Adapter<MenuItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.itemName ?: "Item Name Not Available"
        holder.itemPrice.text = "Price: $${item.price}"
        item.imageURL?.let {
            holder.itemImage.loadImageFromURL(it)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
        var itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
    }
}