package com.alexey.minay.core_navigation

import android.view.View

sealed interface Action {
    object OpenMenu : Action
    object OpenQuotesChart : Action
    object OpenQuotesList : Action
    object Back : Action
    object OpenNews : Action
    class OpenNewsSummary(val newsId: String, val sharedView: View): Action
}