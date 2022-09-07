package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Actor
import com.alexey.minay.core_utils.Result
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.feature_quotes_chart_impl.domain.GetQuotesListUseCase
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesActor @Inject constructor(
    private val getQuotesListUseCase: GetQuotesListUseCase,
    private val gateway: IQuotesChartGateway
) : Actor<QuotesAction, QuotesEffect, QuotesState, QuotesResult>() {

    override suspend fun execute(action: QuotesAction, getState: () -> QuotesState) {
        when (action) {
            QuotesAction.FetchQuotesList -> fetchQuotesList()
            QuotesAction.RefreshQuotesList -> refreshQuotesList()
            QuotesAction.FetchQuotes -> fetchQuotes(getState)
            is QuotesAction.Select -> selectQuotes(action.type)
        }.exhaustive
    }

    private suspend fun fetchQuotesList() {
        val results = getQuotesListUseCase()
        reduce { QuotesResult.UpdateQuotesList(results) }
    }

    private suspend fun refreshQuotesList() {
        reduce { QuotesResult.StartRefreshingList }
        fetchQuotesList()
    }

    private suspend fun fetchQuotes(getState: () -> QuotesState) {
        val selected = getState().listState.selected ?: return
        fetchQuotes(selected.type)
    }

    private suspend fun selectQuotes(type: QuotesType) {
        reduceSuspend { QuotesResult.Select(type) }
        fetchQuotes(type)
    }

    private suspend fun fetchQuotes(type: QuotesType) {
        val result = gateway.getQuotes(type)
        when (result) {
            is Result.Success -> reduce { QuotesResult.UpdateQuotes(result.data) }
            is Result.Error -> Unit
        }.exhaustive
    }

}