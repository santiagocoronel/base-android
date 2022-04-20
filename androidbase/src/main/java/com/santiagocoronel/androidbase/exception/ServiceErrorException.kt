package com.santiagocoronel.androidbase.exception

data class ServiceErrorException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()