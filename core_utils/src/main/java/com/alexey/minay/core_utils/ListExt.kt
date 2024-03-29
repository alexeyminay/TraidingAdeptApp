package com.alexey.minay.core_utils

inline fun <reified T> List<*>.indexOfFirstInstanceOrNull(
    predicate: (T) -> Boolean = { true }
) = indexOfFirst { item ->
    (item as? T)?.let { predicate(it) } ?: false
}