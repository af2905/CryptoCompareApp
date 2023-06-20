package com.af2905.cryptotopandnews.presentation

import com.af2905.cryptotopandnews.presentation.Store.Companion.SINGLE_THREAD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

interface Store<STATE, ACTION, EFFECT> {
    val stateFlow: StateFlow<STATE>
    val actionFlow: SharedFlow<ACTION>
    val effectFlow: Flow<EFFECT>
    suspend fun effect(effect: EFFECT)
    suspend fun dispatch(action: ACTION)
    fun subscribe()

    companion object {
        val SINGLE_THREAD = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }
}

class BaseStore<STATE, ACTION, EFFECT>(
    initialState: STATE,
    private val scope: CoroutineScope,
    private val reducer: Reducer<STATE, ACTION>
) : Store<STATE, ACTION, EFFECT> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow: StateFlow<STATE> = _stateFlow

    private val _actionFlow = MutableSharedFlow<ACTION>()
    override val actionFlow: SharedFlow<ACTION> = _actionFlow

    private val _effectChannel = Channel<EFFECT>(Channel.BUFFERED)
    override val effectFlow: Flow<EFFECT> = _effectChannel.receiveAsFlow()

    override fun subscribe() {
        scope.launch(SINGLE_THREAD) {
            actionFlow.collect { action ->
                _stateFlow.emit(reducer.reduce(stateFlow.value, action))
            }
        }
    }

    override suspend fun dispatch(action: ACTION) {
        scope.launch(SINGLE_THREAD) {
            _actionFlow.emit(action)
        }
    }

    override suspend fun effect(effect: EFFECT) {
        _effectChannel.send(effect)
    }
}