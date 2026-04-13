package com.example.reminder_app

object Utils {
    fun addZero(num: Int): String = if (num < 10) "0$num" else num.toString()
}
