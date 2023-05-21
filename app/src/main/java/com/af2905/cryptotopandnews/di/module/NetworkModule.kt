package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.BuildConfig
import com.af2905.cryptotopandnews.di.module.interceptor.ApiKeyInterceptor
import com.af2905.cryptotopandnews.di.module.interceptor.ErrorInterceptor
import com.af2905.cryptotopandnews.di.module.interceptor.HttpLoggerInterceptor
import com.af2905.cryptotopandnews.di.scope.AppScope
import com.af2905.cryptotopandnews.data.api.NewsApi
import com.af2905.cryptotopandnews.data.api.ToplistsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

const val DEFAULT_TIMEOUT = 30L
const val MAX_IDLE_CONNECTION = 5
const val KEEP_ALIVE_DURATION = 30L

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun providesToplistApi(retrofit: Retrofit): ToplistsApi {
        return retrofit.create(ToplistsApi::class.java)
    }

    @Provides
    @AppScope
    fun providesNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @AppScope
    @Provides
    fun provideRetrofitClient(client: OkHttpClient, converter: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converter)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @AppScope
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @AppScope
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @AppScope
    fun providesRetrofitBuilder(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectionPool(
                ConnectionPool(
                    MAX_IDLE_CONNECTION,
                    KEEP_ALIVE_DURATION,
                    TimeUnit.SECONDS
                )
            )
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }


    @AppScope
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @AppScope
    @Provides
    fun provideErrorInterceptor(): ErrorInterceptor = ErrorInterceptor()

    @AppScope
    @Provides
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @AppScope
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor.Logger = HttpLoggerInterceptor()
}

