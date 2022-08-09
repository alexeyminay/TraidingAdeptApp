package com.alexey.minay.feature_navigation_impl

import android.view.View

sealed interface Screen {
    class Menu(val item: MenuItem) : Screen {
        companion object {
            fun default() = Menu(item = MenuItem.QuotesList)
        }
    }

    object OnBoarding : Screen

    sealed interface MenuItem {
        object QuotesList : MenuItem
        object QuotesChart : MenuItem
        object NewsList : MenuItem
        class NewsSummary(val newsId: String, val sharedView: View) : MenuItem
    }
}