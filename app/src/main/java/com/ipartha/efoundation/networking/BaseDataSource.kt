package com.ipartha.efoundation.networking

import com.ipartha.efoundation.data.Result


abstract class BaseDataSource {

    protected suspend fun <T> getResult(fetchData: suspend () -> T): Result<T> {
        return try {
            val response = fetchData()
            if (response == null) {
                error(" Empty response")
            } else {
                Result.success(response)
            }

        } catch (ex : Exception) {
            error(ex.message ?: ex.toString())
        }

    }

    private fun <T> error(message: String): Result<T> {
        return Result.error("Network call has failed for a following reason: $message")
    }

}