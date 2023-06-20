package com.af2905.cryptotopandnews.presentation

interface Reducer<STATE, ACTION> {
    suspend fun reduce(state: STATE, action: ACTION): STATE
}