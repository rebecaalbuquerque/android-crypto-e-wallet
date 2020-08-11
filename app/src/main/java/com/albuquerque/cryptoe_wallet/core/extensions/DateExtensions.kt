package com.albuquerque.cryptoe_wallet.core.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Transform a [Date] into a [String]
 * @param pattern pattern used in the transformation
 * */
fun Date.format(pattern: String): String = SimpleDateFormat(pattern, Locale("pt", "BR")).format(this)

/**
 * Transform a [String] into a [Date]
 * @param pattern pattern used in the transformation
 * */
fun String.parse(pattern: String? = "dd/MM/yyyy"): Date {
    val format = SimpleDateFormat(pattern, Locale("pt", "BR"))
    var result = Calendar.getInstance().time

    try {
        format.parse(this)?.let {
            result = it
        }

    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return result
}

fun Date.add(field: Int, amount: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(field, -amount)
    return calendar.time
}