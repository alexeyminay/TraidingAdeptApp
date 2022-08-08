package com.alexey.minay.core_ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class SingleStateViewModel<State, Event>(
    initialState: State
) : ViewModel() {

    val state by uiLazy { mState.asStateFlow() }
    val event by uiLazy { mEvent.asSharedFlow() }

    private val mState = MutableStateFlow(initialState)
    private val mEvent = MutableSharedFlow<Event>(extraBufferCapacity = 1)

    protected fun modify(modifier: State.() -> State) {
        mState.value = mState.value.modifier()
    }

    protected fun event(event: Event) {
        mEvent.tryEmit(event)
    }

}