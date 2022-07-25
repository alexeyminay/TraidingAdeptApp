package com.alexey.minay.core_utils

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.modify(modifier: T.() -> T) {
    value = value.modifier()
}