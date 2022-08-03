package com.alexey.minay.feature_quotes_chart_impl.presentation

import com.alexey.minay.base_mvi.Store
import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

@FeatureScope
class QuotesStore @Inject constructor(
    reducer: QuotesReducer,
    actor: QuotesActor,
    initialState: QuotesState
) : Store<QuotesState, QuotesAction, QuotesEffect, QuotesResult>(
    initialState = initialState,
    reducer = reducer,
    actor = actor
)