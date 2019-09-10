package com.dangerfield.barbrasbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    var id: String,
    var name: String
) : Parcelable {
    constructor(source: String) : this("",source)
}
