package com.af2905.cryptotopandnews.di.module.interceptor


import com.af2905.cryptotopandnews.di.module.error.ApiException
import com.af2905.cryptotopandnews.di.module.error.ConnectionException
import com.af2905.cryptotopandnews.di.module.error.ServerException
import com.af2905.cryptotopandnews.di.module.error.UndefinedException
import com.af2905.cryptotopandnews.di.module.error.HttpResponseCode
import com.af2905.cryptotopandnews.di.module.error.ClientException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return handledResponse(chain)
        } catch (apiException: ApiException) {
            throw apiException
        } catch (e: Exception) {
            if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException) {
                throw ConnectionException(e.message)
            }
            throw UndefinedException(e)
        }
    }

    private fun handledResponse(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        }

        when (response.code) {

            in HttpResponseCode.SERVER_ERROR.code -> throw ServerException(response.body.string())

            in HttpResponseCode.BAD_REQUEST.code,
            in HttpResponseCode.CLIENT_ERROR.code -> throw ClientException(response.body.string())

            else -> throw IllegalStateException(
                "Unexpected response with code: ${response.code} and body: ${response.body}"
            )
        }
    }
}