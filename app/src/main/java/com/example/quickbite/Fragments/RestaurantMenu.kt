package com.example.quickbite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.MenuItem

class RestaurantMenu : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: MenuItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false)

        recyclerView = view.findViewById(R.id.itemsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val menuItems = arguments?.getParcelableArrayList<MenuItem>("menuItems")
        val restaurant = arguments?.getString("restaurantName")

        itemsAdapter = MenuItemsAdapter(menuItems ?: emptyList())
        recyclerView.adapter = itemsAdapter

        return view
    }
}


