package com.santiagocoronel.androidbase.domain

abstract class BaseUseCase<T> {

    @Throws(Exception::class)
    abstract suspend fun execute(): T

}