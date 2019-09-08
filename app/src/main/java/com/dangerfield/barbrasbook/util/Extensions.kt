package com.dangerfield.barbrasbook.util

import android.animation.ValueAnimator
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

fun View.rotate(from: Float,to: Float) {
    val valueAnimator = ValueAnimator.ofFloat(from, to)

    valueAnimator.addUpdateListener {
        val value = it.animatedValue as Float
        // 2
        this.rotation = value
    }
    valueAnimator.duration = 300
    valueAnimator.start()
}


