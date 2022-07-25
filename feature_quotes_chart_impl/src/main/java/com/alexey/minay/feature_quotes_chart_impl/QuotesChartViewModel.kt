package com.alexey.minay.feature_quotes_chart_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexey.minay.feature_quotes_chart_impl.data.QuotesChartGateway
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class QuotesChartViewModel : ViewModel() {

    val gateway = QuotesChartGateway()
    val state = MutableStateFlow(QuotesState.default())

    fun fetch() {
        viewModelScope.launch {
            val quotes = gateway.getQuotes()
            if (quotes != null) {
                state.value = state.value.copy(
                    quotes = quotes
                )
            }
        }
    }

}