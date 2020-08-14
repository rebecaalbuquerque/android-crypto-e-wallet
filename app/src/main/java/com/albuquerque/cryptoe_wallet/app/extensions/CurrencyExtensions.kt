package com.albuquerque.cryptoe_wallet.app.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun BigDecimal?.toBrazilianCurrency(): String{
    this?.let {

        return "R$ ".plus(
            (NumberFormat.getNumberInstance(Locale("pt", "BR")) as DecimalFormat).apply {
                applyPattern("#,###,##0.0000")
            }.format(this))

    } ?: kotlin.run {
        return ""
    }

}

fun BigDecimal?.toBrazilianCurrency(zeros: Int = 4): String{
    this?.let {

        return "R$ ".plus(
            (NumberFormat.getNumberInstance(Locale("pt", "BR")) as DecimalFormat).apply {
                applyPattern("#,###,##0.${"0".repeat(zeros)}")
            }.format(this))

    } ?: kotlin.run {
        return ""
    }

}

fun String.toBigDecimal(): BigDecimal {
    return when {
        this.isEmpty() -> BigDecimal(0)
        this.contains("R$ ") -> BigDecimal(this.replace("R$ ", "").replace(".", "#").replace(",", ".").replace("#", ""))
        else -> BigDecimal(this)
    }
}

fun BigDecimal?.plus(value: String?): String {

    return if(this != null && !value.isNullOrEmpty()) {
        this.plus(BigDecimal(value)).toBrazilianCurrency()
    } else {
        ""
    }
}

fun BigDecimal?.minus(value: String?): String {

    return if(this != null && !value.isNullOrEmpty()) {
        this.minus(BigDecimal(value)).toBrazilianCurrency()
    } else {
        ""
    }
}

fun BigDecimal?.times(value: String?): String {

    return if(this != null && !value.isNullOrEmpty()) {
        this.times(BigDecimal(value)).toBrazilianCurrency()
    } else {
        ""
    }
}