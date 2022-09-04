package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.MainMenuItem

data class AppState(
    val screen: Screen,
    val mainMenuState: MainMenuState
) {
    companion object {
        fun default() = AppState(
            screen = Screen.Menu,
            mainMenuState = MainMenuState.default()
        )
    }
}

data class MainMenuState(
    val selectedItem: MainMenuItem,
    val quotesList: Screen.MenuItemScreen,
    val quotesChart: Screen.MenuItemScreen,
    val news: Screen.MenuItemScreen,
) {
    companion object {
        fun default() = MainMenuState(
            selectedItem = MainMenuItem.QUOTES_LIST,
            quotesChart = Screen.MenuItemScreen.QuotesChart,
            quotesList = Screen.MenuItemScreen.QuotesList,
            news = Screen.MenuItemScreen.NewsList
        )
    }

}