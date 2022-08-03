package com.alexey.minay.feature_quotes_chart_impl.presentation

import androidx.lifecycle.viewModelScope
import com.alexey.minay.core_ui.SingleStateViewModel
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.feature_quotes_chart_impl.data.Result
import com.alexey.minay.feature_quotes_chart_impl.domain.IQuotesChartGateway
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesChartViewModel @Inject constructor(
    private val gateway: IQuotesChartGateway
) : SingleStateViewModel<QuotesState, Nothing>(QuotesState.default()) {

    fun fetch() {
        viewModelScope.launch {
            val result = gateway.getQuotes()
            when (result) {
                is Result.Error -> Unit
                is Result.Success -> modify {
                    copy(quotes = quotes)
                }
            }.exhaustive

        }
    }

}