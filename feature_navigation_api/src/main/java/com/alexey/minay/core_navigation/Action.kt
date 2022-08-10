package com.alexey.minay.core_navigation

sealed interface Action {
    object OpenMenu : Action
    object OpenQuotesChart : Action
    object OpenQuotesList : Action
    object Back : Action
    object OpenNewsList : Action
    class OpenNewsSummary(val newsId: String) : Action
}