package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.core_utils.Result
import com.alexey.minay.feature_quotes_chart_impl.domain.ExchangeRateInfo
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation

sealed interface QuotesAction {
    object FetchQuotesList : QuotesAction
    object FetchQuotes : QuotesAction
    object RefreshQuotesList : QuotesAction
}

sealed interface QuotesResult {
    class UpdateQuotesList(val results: Result<List<ExchangeRateInfo>, Nothing>) : QuotesResult
    object StartRefreshingList : QuotesResult
    class UpdateQuotes(val quotes: List<Quotation>) : QuotesResult
}

sealed interface QuotesEffect {

}