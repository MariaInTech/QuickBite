package com.example.quickbite.models

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
    val id: Int,
    val restaurantName: String,
    val description: String,
    val imageURL: String,
    val menu: List<MenuItem>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        mutableListOf<MenuItem>().apply {
            parcel.readTypedList(this, MenuItem.CREATOR)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(restaurantName)
        parcel.writeString(description)
        parcel.writeString(imageURL)
        parcel.writeTypedList(menu)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}
