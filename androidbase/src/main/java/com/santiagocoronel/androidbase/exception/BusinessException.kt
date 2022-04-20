package com.santiagocoronel.androidbase.exception

class BusinessException(
    val title: String,
    val description: String
) : Exception()