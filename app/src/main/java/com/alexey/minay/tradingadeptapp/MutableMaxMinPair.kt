package com.alexey.minay.tradingadeptapp

class MutableMaxMinPair(
    var min: Float,
    var max: Float
) {

    val div: Float
        get() = max - min

}