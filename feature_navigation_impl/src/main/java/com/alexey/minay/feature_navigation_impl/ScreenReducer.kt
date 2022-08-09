package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.Action
import javax.inject.Inject

class ScreenReducer @Inject constructor() {

    fun Screen.reduce(action: Action) = when (action) {
        Action.OpenMenu -> reduceOpenMenu()
        Action.Back -> reduceBack()
        Action.OpenNews -> reduceOpenNews(action)
        Action.OpenQuotesChart -> reduceOpenQuotesChart(action)
        Action.OpenQuotesList -> reduceOpenQuotesList(action)
        is Action.OpenNewsSummary -> reduceOpenNewsSummary(action)
    }

    private fun Screen.reduceOpenMenu() =
        when (this) {
            is Screen.Menu -> this
            Screen.OnBoarding -> Screen.Menu.default()
        }

    private fun Screen.reduceBack() =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesChart -> Screen.Menu(Screen.MenuItem.QuotesList)
                Screen.MenuItem.QuotesList -> TODO()
                is Screen.MenuItem.NewsSummary -> Screen.Menu(Screen.MenuItem.NewsList)
            }
            Screen.OnBoarding -> TODO()
        }

    private fun Screen.reduceOpenNews(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.NewsList -> this
                Screen.MenuItem.QuotesChart,
                Screen.MenuItem.QuotesList -> Screen.Menu(Screen.MenuItem.NewsList)
                is Screen.MenuItem.NewsSummary -> TODO()
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesChart(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesChart -> this
                Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesList -> Screen.Menu(Screen.MenuItem.QuotesChart)
                is Screen.MenuItem.NewsSummary -> TODO()
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesList(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesList -> this
                Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesChart -> Screen.Menu(Screen.MenuItem.QuotesList)
                is Screen.MenuItem.NewsSummary -> TODO()
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenNewsSummary(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesChart,
                is Screen.MenuItem.NewsSummary,
                Screen.MenuItem.QuotesList -> throw NotSupportedPathException(this, action)
                Screen.MenuItem.NewsList -> when (action) {
                    is Action.OpenNewsSummary -> Screen.Menu(
                        Screen.MenuItem.NewsSummary(action.newsId, action.sharedView)
                    )
                    else -> throw NotSupportedPathException(this, action)
                }
            }
            Screen.OnBoarding -> throw NotSupportedPathException(this, action)
        }

    class NotSupportedPathException(
        screen: Screen,
        action: Action
    ) : RuntimeException(
        "Can't handle action $action from screen $screen"
    )

}