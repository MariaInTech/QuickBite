// FirebaseConfigReader.kt
package com.example.quickbite

import android.content.Context
import java.io.IOException
import java.io.InputStream

object FirebaseConfigReader {
    fun getFirebaseKeyInputStream(context: Context): InputStream {
        val assetManager = context.assets
        return assetManager.open("quickbiteAccountKey.json")
    }
}

object FirebaseInitializer {
    fun initializeFirebase(context: Context) {
        try {
            val serviceAccount = FirebaseConfigReader.getFirebaseKeyInputStream(context)
            // Rest of the initialization code
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}