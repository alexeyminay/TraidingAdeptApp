package com.alexey.minay.feature_navigation_impl

sealed interface Screen {
    object Menu : Screen
    object OnBoarding : Screen

    sealed interface MenuItemScreen {
        object QuotesList : MenuItemScreen
        object QuotesChart : MenuItemScreen
        object NewsList : MenuItemScreen
        class NewsSummary(val newsId: String) : MenuItemScreen
    }
}