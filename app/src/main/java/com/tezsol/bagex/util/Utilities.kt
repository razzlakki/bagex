package com.tezsol.bagex.util

import android.content.Context
import android.widget.Toast

class Utilities {
    fun check12DigitLogic(barcode: String): Boolean {
        var flag = false
        try {
            var totalValue = 0
            val values = intArrayOf(5, 1, 7, 5, 1, 7, 5, 1, 7, 5, 1)
            var lastCounter = 0
            for (i in 0..10) {
                val valueAtIndex = barcode[i]
                val intValueAtIndex = (valueAtIndex.toString() + "").toInt()
                val multiplyValue = values[i] * intValueAtIndex
                totalValue = multiplyValue + totalValue
                lastCounter = i
            }
            lastCounter++
            val lastDig = barcode[lastCounter]
            val lastDigInt = (lastDig.toString() + "").toInt()
            var remainder = totalValue % 11
            if (remainder == 10) remainder = 0
            flag = if (remainder == lastDigInt) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            flag = false
            return flag
        }
        return flag
    }

    fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun check15DigitLogic(barcode: String): Boolean {
        return barcode.length == 15
    }


    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}