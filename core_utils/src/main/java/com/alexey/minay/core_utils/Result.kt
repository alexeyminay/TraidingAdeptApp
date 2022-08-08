package com.alexey.minay.core_utils

sealed interface Result<out TData, TError> {
    class Success<TData, TError>(val data: TData) : Result<TData, TError>
    class Error<TData, TError>(val type: ErrorType<TError>) : Result<TData, TError>

    sealed interface ErrorType<TError> {
        class NoInternet<TError> : ErrorType<TError>
        class Unknown<TError> : ErrorType<TError>
        class ServerConnectionError<TError> : ErrorType<TError>
        class Domain<TError>(val domainErrorType: TError) : ErrorType<TError>
    }
}