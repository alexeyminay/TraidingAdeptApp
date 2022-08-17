package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Reducer
import com.alexey.minay.core_utils.DateFormatter
import com.alexey.minay.core_utils.Result
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

    private fun QuotesState.updateQuotesList(result: Result<List<ExchangeRateInfo>, Nothing>): QuotesState {
        val items = mutableListOf<QuotesListItem>()
        items.add(QuotesListItem.Header(QuotesListItem.HeaderType.CURRENCY))
        when (result) {
            is Result.Success ->
                result.data.forEach { info ->
                    items.add(
                        QuotesListItem.Quotes(
                            title = "${info.fromCode}/${info.toCode}",
                            subtitle = "${info.fromName}/${info.fromName}",
                            value = ((info.exchangeRate * 100).roundToInt().toFloat() / 100)
                                .toString(),
                            type = info.type,
                            lastRefreshed = DateFormatter.format1(info.lastRefresh)
                        )
                    )
                }
            is Result.Error -> Unit
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