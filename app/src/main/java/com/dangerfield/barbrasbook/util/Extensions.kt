package com.dangerfield.barbrasbook.util

import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.toReadableDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    var date: Date? = null
    try {
        date = formatter.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.toString().dropLast(18)
}


fun View.showIf(thisIsTrue: Boolean) {
    this.visibility = if(thisIsTrue) View.VISIBLE else View.INVISIBLE
}
