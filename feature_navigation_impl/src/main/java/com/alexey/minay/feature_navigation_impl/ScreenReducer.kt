package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.Action

class ScreenReducer {

    fun Screen.reduce(action: Action) = when (action) {
        Action.OpenMenu -> reduceOpenMenu()
        Action.Back -> reduceBack()
        Action.OpenNew -> reduceOpenNews(action)
        Action.OpenQuotesChart -> reduceOpenQuotesChart(action)
        Action.OpenQuotesList -> reduceOpenQuotesList(action)
    }

    private fun Screen.reduceOpenMenu() =
        when (this) {
            is Screen.Menu -> this
            Screen.OnBoarding -> Screen.Menu.default()
        }

    private fun Screen.reduceBack() =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.News,
                Screen.MenuItem.QuotesChart -> Screen.Menu(Screen.MenuItem.QuotesList)
                Screen.MenuItem.QuotesList -> TODO()
            }
            Screen.OnBoarding -> TODO()
        }

    private fun Screen.reduceOpenNews(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.News -> this
                Screen.MenuItem.QuotesChart,
                Screen.MenuItem.QuotesList -> Screen.Menu(Screen.MenuItem.News)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesChart(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesChart -> this
                Screen.MenuItem.News,
                Screen.MenuItem.QuotesList -> Screen.Menu(Screen.MenuItem.QuotesChart)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesList(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesList -> this
                Screen.MenuItem.News,
                Screen.MenuItem.QuotesChart -> Screen.Menu(Screen.MenuItem.QuotesList)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    class NotSupportedPathException(
        screen: Screen,
        action: Action
    ) : RuntimeException(
        "Can't handle action $action from screen $screen"
    )

}