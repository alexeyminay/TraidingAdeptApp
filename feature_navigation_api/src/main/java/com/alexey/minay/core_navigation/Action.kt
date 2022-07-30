package com.alexey.minay.core_navigation

sealed interface Action {
    object OpenMenu : Action
    object OpenQuotesChart : Action
    object OpenQuotesList : Action
    object OpenNews : Action
    object Back : Action
}