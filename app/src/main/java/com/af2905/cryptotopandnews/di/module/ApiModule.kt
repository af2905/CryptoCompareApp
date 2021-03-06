package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.di.scope.ApiScope
import com.af2905.cryptotopandnews.repository.server.ApiService
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import com.squareup.picasso.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {
    @Provides
    @ApiScope
    fun providesServerCommunicator(apiService: ApiService): ServerCommunicator {
        return ServerCommunicator(apiService)
    }

    @Provides
    @ApiScope
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @ApiScope
    fun providesRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(BASE_URL).build()
    }

    @Provides
    @ApiScope
    fun providesRetrofitBuilder(): Retrofit.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    companion object {
        private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    }
}
