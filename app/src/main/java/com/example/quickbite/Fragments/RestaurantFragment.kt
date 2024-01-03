package com.example.quickbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbite.models.Restaurant
import com.example.quickbite.RestaurantsAdapter
import com.example.quickbite.com.example.quickbite.util.AppViewModel
import com.example.quickbite.models.MenuItem

class RestaurantFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.RestaurantsRV)

        viewModel.restaurants.observe(viewLifecycleOwner, Observer { restaurants ->
            val adapter = RestaurantsAdapter(restaurants, requireContext(), requireActivity().supportFragmentManager)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        })

        viewModel.fetchRestaurants()

        return view
    }

    companion object {

    }
}
