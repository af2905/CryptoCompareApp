package com.af2905.cryptotopandnews.di.module.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HttpLoggerInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) = Timber.tag("OkHttp").d(message)
}