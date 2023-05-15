package com.af2905.cryptotopandnews.di.module.interceptor

import com.af2905.cryptotopandnews.BuildConfig
import com.af2905.cryptotopandnews.repository.api.ApiParams
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(ApiParams.API_KEY, BuildConfig.API_KEY).build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}