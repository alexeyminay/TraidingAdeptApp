package com.alexey.minay.feature_navigation_impl

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
        object News : MenuItem
    }
}