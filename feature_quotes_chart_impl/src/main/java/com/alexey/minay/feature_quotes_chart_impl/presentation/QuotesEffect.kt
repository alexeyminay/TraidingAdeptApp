package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.feature_quotes_chart_impl.data.Result
import com.alexey.minay.feature_quotes_chart_impl.domain.ExchangeRateInfo

sealed interface QuotesAction {
    object FetchQuotesList: QuotesAction
    object RefreshQuotesList : QuotesAction
}

sealed interface QuotesResult {
    class UpdateQuotesList(val results: List<Result<ExchangeRateInfo, Nothing>>): QuotesResult
    object StartRefreshingList: QuotesResult
}

sealed interface QuotesEffect {

}