package com.dangerfield.barbrasbook.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.toReadableDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    var date: Date? = null
    try {
        date = formatter.parse(this)
        println(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    Log.d("Date",date.toString())
    return date.toString().dropLast(18)
}