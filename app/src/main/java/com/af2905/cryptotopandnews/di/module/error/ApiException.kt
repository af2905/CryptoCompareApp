package com.af2905.cryptotopandnews.di.module.error

import java.io.IOException

abstract class ApiException(message: String?) : IOException(message)

class UndefinedException(throwable: Throwable) : ApiException(throwable.message) {
    init {
        addSuppressed(throwable)
    }
}

class ConnectionException(message: String?) : ApiException(message)

open class ErrorBodyApiException(message: String?) : ApiException(message)

class ClientException(message: String?) : ErrorBodyApiException(message)
class ServerException(message: String?) : ErrorBodyApiException(message)