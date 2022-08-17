package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.core_utils.Result
import com.alexey.minay.feature_quotes_chart_impl.domain.ExchangeRateInfo

sealed interface QuotesAction {
    object FetchQuotesList : QuotesAction
    object RefreshQuotesList : QuotesAction
}

sealed interface QuotesResult {
    class UpdateQuotesList(val results: Result<List<ExchangeRateInfo>, Nothing>) : QuotesResult
    object StartRefreshingList : QuotesResult
}

sealed interface QuotesEffect {

}