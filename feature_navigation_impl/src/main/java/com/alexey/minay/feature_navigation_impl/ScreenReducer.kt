package com.alexey.minay.feature_navigation_impl

import com.alexey.minay.core_navigation.Action
import javax.inject.Inject

class ScreenReducer @Inject constructor() {

    fun Screen.reduce(action: Action) = when (action) {
        Action.OpenMenu -> reduceOpenMenu()
        Action.Back -> reduceBack()
        is Action.OpenNewsList -> reduceOpenNewsList(action)
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
                is Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesChart -> Screen.Menu(Screen.MenuItem.QuotesList)
                Screen.MenuItem.QuotesList -> TODO()
                is Screen.MenuItem.NewsSummary -> Screen.Menu(Screen.MenuItem.NewsList)
            }
            Screen.OnBoarding -> TODO()
        }

    private fun Screen.reduceOpenNewsList(action: Action.OpenNewsList) =
        when (this) {
            is Screen.Menu -> when (item) {
                is Screen.MenuItem.NewsList -> this
                Screen.MenuItem.QuotesChart,
                Screen.MenuItem.QuotesList,
                is Screen.MenuItem.NewsSummary ->
                    Screen.Menu(Screen.MenuItem.NewsList)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesChart(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesChart -> this
                is Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesList,
                is Screen.MenuItem.NewsSummary -> Screen.Menu(Screen.MenuItem.QuotesChart)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenQuotesList(action: Action) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesList -> this
                is Screen.MenuItem.NewsList,
                Screen.MenuItem.QuotesChart,
                is Screen.MenuItem.NewsSummary -> Screen.Menu(Screen.MenuItem.QuotesList)
            }
            else -> throw NotSupportedPathException(this, action)
        }

    private fun Screen.reduceOpenNewsSummary(action: Action.OpenNewsSummary) =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.QuotesChart,
                is Screen.MenuItem.NewsSummary,
                Screen.MenuItem.QuotesList -> throw NotSupportedPathException(this, action)
                is Screen.MenuItem.NewsList -> Screen.Menu(
                    Screen.MenuItem.NewsSummary(action.newsId)
                )
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