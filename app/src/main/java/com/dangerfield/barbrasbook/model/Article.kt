package com.dangerfield.barbrasbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Article(val title: String, val image: String, val text: String) : Parcelable {
}