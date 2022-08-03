package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Reducer
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesReducer @Inject constructor() : Reducer<QuotesResult, QuotesState> {
    override fun QuotesState.reduce(result: QuotesResult): QuotesState {
        TODO()
    }
}