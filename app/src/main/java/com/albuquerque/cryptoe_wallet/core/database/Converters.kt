package com.albuquerque.cryptoe_wallet.core.database

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(value:BigDecimal):String{
        return value.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toBigDecimal(value:String): BigDecimal {
        return value.toBigDecimal()
    }

}