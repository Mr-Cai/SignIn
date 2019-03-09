package com.example.kk.mycalendercard

import java.util.*

object DateUtil {

    var weekName = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    val year: Int
        get() = Calendar.getInstance().get(Calendar.YEAR)

    val month: Int
        get() = Calendar.getInstance().get(Calendar.MONTH) + 1


    val day: Int
        get() = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    //今天2号周四
    //返回5
    //0001234
    //1234567
    //7123456
    val weekDay: Int
        get() = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)


    val hour: Int
        get() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = Calendar.getInstance().get(Calendar.MINUTE)

    fun getMonthDays(year: Int, month: Int): Int {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year += 1
        } else if (month < 1) {
            month = 12
            year -= 1
        }
        val arr = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var days = 0

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            arr[1] = 29
        }

        try {
            days = arr[month - 1]
        } catch (e: Exception) {
            e.stackTrace
        }

        return days
    }

    fun getDayOfWeek(y: Int, c: Int, m: Int, d: Int): Int {
        var year = y
        var month = m
        if (month == 1) {
            month = 13
            year -= 1
        } else if (month == 2) {
            month = 14
            year -= 1
        }
        var tempDate = (year + year / 4 + c / 4 - 2 * c + 26 * (month + 1) / 10 + d - 1) % 7
        if (tempDate < 0) {
            tempDate += 7
        }

        //7123456
        //560123
        var data = 1
        when (tempDate) {
            0 -> data = 2
            1 -> data = 3
            2 -> data = 4
            3 -> data = 5
            4 -> data = 6
            5 -> data = 7
            6 -> data = 1
        }
        return data
    }

}
