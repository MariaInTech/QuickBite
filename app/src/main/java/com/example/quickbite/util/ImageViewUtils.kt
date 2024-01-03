package com.example.quickbite.com.example.quickbite.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.example.quickbite.R

fun ImageView.loadImageFromURL(url: String){
    Glide.with(this).load(url).placeholder(R.drawable.mcdo)
        .error(R.drawable.mcdo).into(this)
}