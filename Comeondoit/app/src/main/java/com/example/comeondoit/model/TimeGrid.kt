package com.example.comeondoit.model

import android.os.Parcelable
import android.view.View
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeGrid(
        val timeStart: String,
        val timeEnd: String
) : Parcelable
