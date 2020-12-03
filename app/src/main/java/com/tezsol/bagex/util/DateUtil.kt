package com.tezsol.bagex.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    fun getFormatDate(pattern: String, date: Date): String {
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(date)
        return date;
    }

    fun getDateFromString(pattern: String, dateString: String): Date {
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.parse(dateString)
    }

}