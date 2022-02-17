package com.example.myapplication.repository.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResource<T> =
        withContext(Dispatchers.IO) {
            try {
                NetworkResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                wrapError(throwable)
            }
        }

    fun wrapError(throwable: Throwable) = when (throwable) {
        is HttpException -> NetworkResource.Failure(
            false,
            throwable.code(),
            throwable.response()?.errorBody()
        )
        else -> NetworkResource.Failure(true, null, null)
    }
}