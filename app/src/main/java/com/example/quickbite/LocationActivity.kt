package com.example.quickbite

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
        // Check and request location permission
        if (checkLocationPermission()) {
            // Initialize MapView
            mapView = findViewById(R.id.mapView)
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)

            // Set a mock location (replace latitude and longitude with your desired values)
            val mockLocation = LatLng(37.7749, -122.4194) // Example coordinates (San Francisco)
            mapView.onResume() // Needed to trigger the OnMapReadyCallback
        } else {
            requestLocationPermission()
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
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}







/*package com.example.quickbite

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.Locale



class LocationActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1 // Replace with your actual constant value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        } else {
            // Permission already granted, proceed with location access
            getLocation()
        }
    }
    private fun getLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            // Now you can use these coordinates to get the location name
            getLocationName(latitude, longitude)
        }
    }
    private fun getLocationName(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.size > 0) {
                val locationName: String = addresses[0].getAddressLine(0)
                // Now you have the location name, you can save it or display it as needed
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}*/