package com.example.artgallery.utils

import retrofit2.Response

typealias NullCheckAction<T> = (T) -> Boolean
typealias AdditionalCheckAction<T> = (T) -> String?

const val nullErrorMessage = "The service response was successful but the body was null"

inline fun <T> checkForUnsuccessfulResponses(
    response: Response<T>,
    noinline additionalCheckAction: AdditionalCheckAction<T>? = null,
    nullCheck: NullCheckAction<T>
): T {
    if (!response.isSuccessful)
        throw Exception("Http Code: ${response.code()}.\nMessage: ${response.message()}")

    val body = response.body()

    if (body == null || nullCheck(body))
        throw NullPointerException(nullErrorMessage)

    additionalCheckAction?.let { action ->
        action(body)?.let {
            throw Exception(it)
        }
    }

    return body
}