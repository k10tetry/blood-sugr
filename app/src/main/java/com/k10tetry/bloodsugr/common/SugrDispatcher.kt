package com.k10tetry.bloodsugr.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface SugrDispatcher {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}

class SugrDispatcherImpl @Inject constructor() : SugrDispatcher {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val immediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
}

