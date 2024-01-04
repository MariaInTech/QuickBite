package com.example.quickbite


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private val locationPermissionCode = 123
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Check and request location permission
        if (checkLocationPermission()) {
            initializeMap(savedInstanceState)
        } else {
            requestLocationPermission()
        }
    }

    private fun initializeMap(savedInstanceState: Bundle?) {
        // Initialize MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Set a mock location (replace latitude and longitude with your desired values)
        val mockLocation = LatLng(37.7749, -122.4194) // Example coordinates (San Francisco)
        mapView.onResume() // Needed to trigger the OnMapReadyCallback

        // Add click listener to place a marker on the map
        mapView.getMapAsync { map ->
            map.setOnMapClickListener { latLng ->
                map.clear() // Clear existing markers
                map.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
                // You can save the selected location (latLng) for further use
                Toast.makeText(
                    this,
                    "Location selected: ${latLng.latitude}, ${latLng.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Enable My Location button and show the user's location
        enableMyLocation()

        // Example: Move camera to a specific location
        val location = LatLng(37.7749, -122.4194) // Example coordinates (San Francisco)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in San Francisco"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

    private fun enableMyLocation() {
        if (checkLocationPermission()) {
            // Enable My Location button and show the user's location
            try {
                googleMap.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                // Handle exception, e.g., request permission or show a message to the user
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionCode && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            initializeMap(savedInstanceState)
        } else {
            // Handle permission denial
            // You may show a message to the user explaining why you need the permission
        }
    }

    override fun onResume() {
        super.onResume()
        if (::mapView.isInitialized && mapView != null) {
            mapView.onResume()
        }
    }


    override fun onPause() {
        super.onPause()
        if (::mapView.isInitialized && mapView != null) {
            mapView.onPause()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (::mapView.isInitialized && mapView != null) {
            mapView.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (::mapView.isInitialized && mapView != null) {
            mapView.onLowMemory()
        }
    }
}
