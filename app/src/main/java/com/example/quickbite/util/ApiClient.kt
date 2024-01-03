package com.example.quickbite

import android.util.Log
import com.example.quickbite.models.MenuItem
import com.example.quickbite.models.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("restaurant")
    suspend fun getRestaurants(): Response<List<Restaurant>>

    @GET("restaurant/{restaurantId}/menu")
    suspend fun getMenuItems(@Path("restaurantId") restaurantId: Int): Response<List<MenuItem>>
}

object ApiClient {
    private const val BASE_URL = "https://658d8e7a7c48dce947396672.mockapi.io/api/quickbite_restaurants/"
    private val TAG: String = "CHECK_RESPONSE"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(MyApi::class.java)

    suspend fun getAllRestaurants(): Response<List<Restaurant>> {
        return api.getRestaurants()
    }
    suspend fun getMenuItems(restaurantId: Int): Response<List<MenuItem>> {
        return api.getMenuItems(restaurantId)
    }

}
