package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.MainMenuItem
import javax.inject.Inject

class ScreenReducer @Inject constructor() {

    fun AppState.reduce(action: Action) = when (action) {
        Action.OpenMenu -> reduceOpenMenu()
        is Action.SelectNewsListItem -> reduceSelectNewsListIItem()
        Action.SelectQuotesChartItem -> reduceNewsQuotesChartItem()
        Action.SelectQuotesListItem -> reduceSelectQuotesListItem()
        is Action.OpenNewsSummary -> reduceOpenNewsSummary(action)
        Action.OpenNewsList -> reduceOpenNewsList()
        is Action.OpenNews -> reduceOpenNews(action)
    }

    private fun AppState.reduceOpenMenu() =
        when (this.screen) {
            is Screen.Menu -> this
            Screen.OnBoarding,
            is Screen.News -> copy(screen = Screen.Menu)
        }

    private fun AppState.reduceSelectNewsListIItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuItem.NEWS_LIST))

    private fun AppState.reduceNewsQuotesChartItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuItem.QUOTES_CHART))

    private fun AppState.reduceSelectQuotesListItem() =
        copy(mainMenuState = mainMenuState.copy(selectedItem = MainMenuItem.QUOTES_LIST))

    private fun AppState.reduceOpenNewsSummary(action: Action.OpenNewsSummary) =
        copy(
            screen = Screen.Menu,
            mainMenuState = mainMenuState.copy(
                selectedItem = MainMenuItem.NEWS_LIST,
                news = Screen.MenuItemScreen.NewsSummary(action.newsId)
            )
        )

    private fun AppState.reduceOpenNewsList() =
        copy(
            mainMenuState = mainMenuState.copy(
                selectedItem = MainMenuItem.NEWS_LIST,
                news = Screen.MenuItemScreen.NewsList
            )
        )

    private fun AppState.reduceOpenNews(action: Action.OpenNews) =
        when (this.screen) {
            is Screen.News -> this
            Screen.OnBoarding,
            Screen.Menu -> copy(screen = Screen.News(action.url, action.newsId))
        }

}