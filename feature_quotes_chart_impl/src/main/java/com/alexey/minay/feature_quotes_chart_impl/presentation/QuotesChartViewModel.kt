package com.alexey.minay.feature_quotes_chart_impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexey.minay.feature_quotes_chart_impl.data.QuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class QuotesChartViewModel(
    //private val gateway: IQuotesChartGateway
) : ViewModel() {

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