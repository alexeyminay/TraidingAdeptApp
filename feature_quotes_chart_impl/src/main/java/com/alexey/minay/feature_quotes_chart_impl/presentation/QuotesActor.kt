package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Actor
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesActor @Inject constructor(): Actor<QuotesAction, QuotesEffect, QuotesState, QuotesResult>() {
    override suspend fun execute(action: QuotesAction, getState: () -> QuotesState) {
        TODO("Not yet implemented")
    }
}