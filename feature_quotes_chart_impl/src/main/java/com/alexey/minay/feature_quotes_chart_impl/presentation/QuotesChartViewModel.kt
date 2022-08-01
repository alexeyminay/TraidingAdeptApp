package com.alexey.minay.feature_quotes_chart_impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesChartViewModel @Inject constructor(
    private val gateway: IQuotesChartGateway
) : ViewModel() {

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