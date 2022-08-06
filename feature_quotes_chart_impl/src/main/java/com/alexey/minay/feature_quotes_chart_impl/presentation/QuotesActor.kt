package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Actor
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.feature_quotes_chart_impl.domain.GetQuotesListUseCase
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesActor @Inject constructor(
    private val getQuotesListUseCase: GetQuotesListUseCase
) : Actor<QuotesAction, QuotesEffect, QuotesState, QuotesResult>() {

    override suspend fun execute(action: QuotesAction, getState: () -> QuotesState) {
        when (action) {
            QuotesAction.FetchQuotesList -> fetchQuotesList()
        }.exhaustive
    }

    private suspend fun fetchQuotesList() {
        val results = getQuotesListUseCase()
        reduce { QuotesResult.UpdateQuotesList(results) }
    }

}