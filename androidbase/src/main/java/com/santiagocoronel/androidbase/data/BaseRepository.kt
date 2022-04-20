package com.santiagocoronel.androidbase.data

import com.santiagocoronel.androidbase.exception.BusinessException
import com.santiagocoronel.androidbase.exception.GenericException
import com.santiagocoronel.androidbase.exception.UnAuthorizeException
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection


abstract class BaseRepository {

    private val ERROR_BUSSINES = 422

    @Throws(Exception::class, BusinessException::class)
    protected fun <T> processResponse(response: Response<T>): T {
        return when (response.code()) {
            HttpsURLConnection.HTTP_OK,
            HttpsURLConnection.HTTP_CREATED,
            HttpsURLConnection.HTTP_NO_CONTENT,
            HttpsURLConnection.HTTP_ACCEPTED -> response.body()!!
            206 -> throw parseBusinessMedicatoException(body = response.errorBody())
            HttpURLConnection.HTTP_UNAUTHORIZED -> throw UnAuthorizeException()
            HttpURLConnection.HTTP_FORBIDDEN,
            HttpURLConnection.HTTP_BAD_REQUEST,
            ERROR_BUSSINES -> throw parseBusinessException(errorBody = response.errorBody())
            else -> throw GenericException()
        }
    }

    private fun parseBusinessMedicatoException(body: ResponseBody?): BusinessException {
        body.toString()
        //here need to process de error response
        return BusinessException(title = "", description = "")
    }

    private fun parseBusinessException(errorBody: ResponseBody?): BusinessException {
        //here need to process de error response
        return BusinessException(title = "", description = "")
    }

    protected fun handleCatch(throwable: Throwable) : com.santiagocoronel.androidbase.data.Response.Failure<*> {
        throwable.printStackTrace()
        return when {
            throwable.message?.contains("Unable to resolve host") == true -> {
                com.santiagocoronel.androidbase.data.Response.Failure(UnknownHostException(throwable.message))
            }
            throwable.message?.contains("I/O error during system call") == true -> {
                com.santiagocoronel.androidbase.data.Response.Failure(UnknownHostException(throwable.message))
            }
            else -> {
                com.santiagocoronel.androidbase.data.Response.Failure(Exception(throwable))
            }
        }
    }
}