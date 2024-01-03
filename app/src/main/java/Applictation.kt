package com.example.quickbite
import android.app.Application


class Application:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseInitializer.initializeFirebase(this)
    }
}