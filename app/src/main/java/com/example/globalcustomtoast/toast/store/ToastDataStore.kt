package com.example.globalcustomtoast.toast.store

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

class ToastDataStore {

    private val _toasts = Channel<ToastData>(capacity = Channel.UNLIMITED)
    val toasts = _toasts.consumeAsFlow()

    suspend fun enqueue(data: ToastData) {
        _toasts.send(data)
    }

    companion object {

        private val globalStore = ToastDataStore()

        fun getToastDataStore(): ToastDataStore = globalStore
    }
}