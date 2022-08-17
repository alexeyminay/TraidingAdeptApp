package com.alexey.minay.feature_quotes_chart_impl.domain

import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetQuotesListUseCase @Inject constructor(
    private val storage: ISupportedQuotesStorage,
    private val gateway: IQuotesListGateway
) {

    suspend operator fun invoke() = coroutineScope {
        gateway.getCurrency(storage.getCurrencies())
    }

}