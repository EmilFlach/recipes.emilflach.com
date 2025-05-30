package com.emilflach.recipes.utils

import kotlin.math.pow

object NumberUtils {
    fun Double.toFixed(decimals: Int): String {
        val multiplier = 10.0.pow(decimals)
        val rounded = kotlin.math.round(this * multiplier) / multiplier
        return rounded.toString().let {
            if (!it.contains(".")) it + "." + "0".repeat(decimals)
            else {
                val parts = it.split(".")
                parts[0] + "." + parts[1].padEnd(decimals, '0')
            }
        }
    }

    fun Double.formatQuantity(): String {
        return if (this % 1 == 0.0) {
            toInt().toString()
        } else {
            toFixed(2)
        }
    }
}
