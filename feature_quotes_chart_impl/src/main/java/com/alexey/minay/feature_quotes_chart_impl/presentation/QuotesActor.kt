package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Actor
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_utils.Result
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.feature_quotes_chart_impl.domain.GetQuotesListUseCase
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.QuotesType
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesActor @Inject constructor(
    private val getQuotesListUseCase: GetQuotesListUseCase,
    private val gateway: IQuotesChartGateway,
    private val navigator: INavigator
) : Actor<QuotesAction, QuotesEffect, QuotesState, QuotesResult>() {

    override suspend fun execute(action: QuotesAction, getState: () -> QuotesState) {
        when (action) {
            QuotesAction.FetchQuotesList -> fetchQuotesList(getState)
            QuotesAction.RefreshQuotesList -> refreshQuotesList(getState)
            QuotesAction.FetchQuotes -> fetchQuotes(getState)
            is QuotesAction.Select -> selectQuotes(action.type)
        }.exhaustive
    }

    private suspend fun fetchQuotesList(getState: () -> QuotesState) {
        val results = getQuotesListUseCase()
        reduceSuspend { QuotesResult.UpdateQuotesList(results) }
        fetchQuotes(getState)
    }

    private suspend fun refreshQuotesList(getState: () -> QuotesState) {
        reduce { QuotesResult.StartRefreshingList }
        fetchQuotesList(getState)
    }

    private suspend fun fetchQuotes(getState: () -> QuotesState) {
        val selected = getState().listState.selected ?: return
        fetchQuotes(selected.type)
    }

    private suspend fun selectQuotes(type: QuotesType) {
        reduceSuspend { QuotesResult.Select(type) }
        navigator.perform(Action.SelectQuotesChartItem)
        fetchQuotes(type)
    }

    private suspend fun fetchQuotes(type: QuotesType) {
        reduce { QuotesResult.SetChartRefreshing }
        val result = gateway.getQuotes(type)
        when (result) {
            is Result.Success -> reduce { QuotesResult.UpdateQuotes(result.data) }
            is Result.Error -> Unit
        }.exhaustive
    }

}