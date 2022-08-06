package com.alexey.minay.feature_quotes_chart_impl.data

import javax.inject.Inject

class RequestWrapper @Inject constructor() {

    fun <TResult, TError> wrap(perform: () -> TResult?): Result<TResult, TError> {
        return try {
            val data = perform() ?: return Result.Error(Result.ErrorType.Unknown())
            Result.Success(data)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(Result.ErrorType.ServerConnectionError())
        }
    }

}