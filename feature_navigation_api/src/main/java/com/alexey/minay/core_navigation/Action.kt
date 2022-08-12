package com.alexey.minay.core_navigation

sealed interface Action {
    object OpenMenu : Action
    object SelectQuotesChartItem : Action
    object SelectQuotesListItem : Action
    object SelectNewsListItem : Action
    class OpenNewsSummary(val newsId: String) : Action
    object OpenNewsList : Action
    class OpenNews(val url: String, val newsId: String) : Action
}