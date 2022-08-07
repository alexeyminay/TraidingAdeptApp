package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Reducer
import com.alexey.minay.core_utils.DateFormatter
import com.alexey.minay.feature_quotes_chart_impl.data.Result
import com.alexey.minay.feature_quotes_chart_impl.domain.ExchangeRateInfo
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListItem
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListState
import javax.inject.Inject
import kotlin.math.roundToInt

class QuotesReducer @Inject constructor() : Reducer<QuotesResult, QuotesState> {

    override fun QuotesState.reduce(result: QuotesResult) = when (result) {
        is QuotesResult.UpdateQuotesList -> updateQuotesList(result.results)
        QuotesResult.StartRefreshingList -> startRefreshingList()
    }

    private fun QuotesState.updateQuotesList(results: List<Result<ExchangeRateInfo, Nothing>>): QuotesState {
        val items = mutableListOf<QuotesListItem>()
        items.add(QuotesListItem.Header(QuotesListItem.HeaderType.CURRENCY))
        results.forEach { result ->
            when (result) {
                is Result.Success ->
                    items.add(
                        QuotesListItem.Quotes(
                            title = "${result.data.fromCode}/${result.data.toCode}",
                            subtitle = "${result.data.fromName}/${result.data.fromName}",
                            value = ((result.data.exchangeRate * 100).roundToInt().toFloat() / 100)
                                .toString(),
                            type = result.data.type,
                            lastRefreshed = DateFormatter.format1(result.data.lastRefresh)
                        )
                    )
                is Result.Error -> Unit
            }
        }

        return copy(
            listState = listState.copy(
                items = items,
                isRefreshing = false,
                type = when (items.size) {
                    1 -> QuotesListState.Type.EMPTY
                    else -> QuotesListState.Type.DATA
                }
            )
        )
    }

    private fun QuotesState.startRefreshingList() =
        copy(listState = listState.copy(isRefreshing = true))

}