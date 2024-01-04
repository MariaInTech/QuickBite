package com.example.quickbite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.com.example.quickbite.util.loadImageFromURL
import com.example.quickbite.models.Restaurant

class SearchResultAdapter(private var data: List<Restaurant>) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RestaurantPageActivity::class.java)
            intent.putExtra("selectedRestaurant", item)
            holder.itemView.context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Restaurant>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val resultImageView: ImageView = itemView.findViewById(R.id.resultImageView)
        private val resultTextView: TextView = itemView.findViewById(R.id.resultTextView)

        fun bind(item: Restaurant) {
            resultTextView.text = item.restaurantName

            resultImageView.loadImageFromURL(item.imageURL)

        }
    }
}