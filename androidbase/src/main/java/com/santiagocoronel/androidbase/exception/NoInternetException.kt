package com.santiagocoronel.androidbase.exception

data class NoInternetException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()