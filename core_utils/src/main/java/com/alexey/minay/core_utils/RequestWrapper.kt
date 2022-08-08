package com.alexey.minay.core_utils

class RequestWrapper {

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