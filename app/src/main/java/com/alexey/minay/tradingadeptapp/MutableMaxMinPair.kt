package com.alexey.minay.tradingadeptapp

class MutableMaxMinPair(
    var min: Float,
    var max: Float
) {

    val div: Float
        get() = max - min

    fun reset() {
        min = Float.MAX_VALUE
        max = Float.MIN_VALUE
    }

}