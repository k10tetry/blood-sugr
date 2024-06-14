package com.k10tetry.bloodsugr.presentation

import com.k10tetry.bloodsugr.common.SugrDispatcher
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherImpl(private val dispatcher: CoroutineDispatcher) : SugrDispatcher {
    override val io: CoroutineDispatcher
        get() = dispatcher
    override val main: CoroutineDispatcher
        get() = dispatcher
    override val default: CoroutineDispatcher
        get() = dispatcher
    override val unconfined: CoroutineDispatcher
        get() = dispatcher
    override val immediate: CoroutineDispatcher
        get() = dispatcher
}