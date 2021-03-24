package com.example.memoryfinder.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemoryImg (
        val name: String,
        val photographer : String,
        val image: String
        ):Parcelable


