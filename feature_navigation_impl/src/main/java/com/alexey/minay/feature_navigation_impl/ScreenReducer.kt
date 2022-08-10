package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.Action
import javax.inject.Inject

class ScreenReducer @Inject constructor() {

    fun AppState.reduce(action: Action) = when (action) {
        Action.OpenMenu -> reduceOpenMenu()
        is Action.SelectNewsListItem -> reduceSelectNewsListIItem()
        Action.SelectQuotesChartItem -> reduceNewsQuotesChartItem()
        Action.SelectQuotesListItem -> reduceSelectQuotesListItem()
        is Action.OpenNewsSummary -> reduceOpenNewsSummary(action)
        Action.OpenNewsList -> reduceOpenNewsList()
    }

    private fun AppState.reduceOpenMenu() =
        when (this.screen) {
            is Screen.Menu -> this
            Screen.OnBoarding -> copy(screen = Screen.Menu)
        }

    private fun AppState.reduceSelectNewsListIItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuState.MainMenuItem.NEWS_LIST))

    private fun AppState.reduceNewsQuotesChartItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuState.MainMenuItem.QUOTES_CHART))

    private fun AppState.reduceSelectQuotesListItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuState.MainMenuItem.QUOTES_LIST))

    private fun AppState.reduceOpenNewsSummary(action: Action.OpenNewsSummary) =
        copy(
            mainMenuState = mainMenuState.copy(
                selectedItem = MainMenuState.MainMenuItem.NEWS_LIST,
                news = Screen.MenuItemScreen.NewsSummary(action.newsId)
            )
        )

    private fun AppState.reduceOpenNewsList() =
        copy(
            mainMenuState = mainMenuState.copy(
                selectedItem = MainMenuState.MainMenuItem.NEWS_LIST,
                news = Screen.MenuItemScreen.NewsList
            )
        )

}