package com.alexey.minay.feature_quotes_chart_impl.data

sealed interface Result<out TData, TError> {
    class Success<TData, TError>(val data: TData) : Result<TData, TError>
    class Error<TData, TError>(val type: ErrorType<TError>) : Result<TData, TError>

    sealed interface ErrorType<TError> {
        class NoInternet<TError> : ErrorType<TError>
        class ServerConnectionError<TError> : ErrorType<TError>
        class Domain<TError>(val domainErrorType: TError) : ErrorType<TError>
    }
}