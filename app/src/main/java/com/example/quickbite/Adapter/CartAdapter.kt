package com.example.quickbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.CartItem
import com.bumptech.glide.Glide

class CartAdapter(private val cartItems: List<CartItem>, private val listener: CartItemClickListener) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface CartItemClickListener {
        fun onQuantityChange(position: Int, newQuantity: Int)
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val increaseQuantity: ImageView = itemView.findViewById(R.id.increaseQuantity)
        val decreaseQuantity: ImageView = itemView.findViewById(R.id.decreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]

        holder.itemName.text = currentItem.itemName
        holder.itemQuantity.text = currentItem.quantity.toString()
        holder.itemPrice.text = "$${currentItem.price}"

        Glide.with(holder.itemView)
            .load(currentItem.imageResourceURL)
            .into(holder.itemImage)

        holder.increaseQuantity.setOnClickListener {
            listener.onQuantityChange(position, currentItem.quantity + 1)
        }

        holder.decreaseQuantity.setOnClickListener {
            if (currentItem.quantity > 1) {
                listener.onQuantityChange(position, currentItem.quantity - 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
