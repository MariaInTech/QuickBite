package com.example.quickbite.com.example.quickbite.util

import android.content.ContentValues
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

//Use It in case we need an API Key
class HTTPConnector {
    private fun createConnection(path: String, method: String, authRequired: Boolean): HttpsURLConnection {
        val url = URL("BuildConfig.SERVER_URL$path")
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = method
        if (authRequired) {
            connection.setRequestProperty("Authorization", "Bearer ${"BuildConfig.API_KEY"}")
        }
        return connection
    }

    private fun getConnectionResponseAsString(connection: HttpsURLConnection): String{
        val inputStream = connection.inputStream
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append('\n')
        }

        bufferedReader.close()
        return bufferedReader.toString()
    }

    fun createRequest(path: String, method: String = "GET", authRequired: Boolean = true): String?{
        try {
            val connection = createConnection(path, method, authRequired)
            connection.connect()
            return getConnectionResponseAsString(connection)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error fetching news: ${e.message}")
        }
        return null
    }
}