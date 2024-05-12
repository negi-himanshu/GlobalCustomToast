package com.example.globalcustomtoast.toast.consume

import com.example.globalcustomtoast.toast.store.ToastData
import com.example.globalcustomtoast.toast.store.ToastDataStore
import com.example.globalcustomtoast.toast.store.ToastDuration
import com.example.globalcustomtoast.toast.store.ToastDurationConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToastConsumer(
    private val toastDataStore: ToastDataStore,
    private val scope: CoroutineScope
) {

    private val _currentToast: MutableStateFlow<ToastData?> = MutableStateFlow(null)
    val currentToast = _currentToast.asStateFlow()

    private val messageProcessedNotifier = Channel<Boolean>()

    init {
        scope.launch(Dispatchers.IO) {
            toastDataStore.toasts.collect {
                _currentToast.value = it
                messageProcessedNotifier.receive()
                // For simulating message going out and coming inside screen
                delay(DELAY_BETWEEN_MESSAGE)
            }
        }
    }

    fun onToastDisappear() {
        scope.launch(Dispatchers.IO) {
            messageProcessedNotifier.send(true)
        }
        _currentToast.value = null
    }

    fun getToastDuration(@ToastDuration type: Int): Long {
        return when (type) {
            ToastDuration.SHORT -> ToastDurationConstants.SHORT
            ToastDuration.LONG -> ToastDurationConstants.LONG
            else -> ToastDurationConstants.SHORT
        }
    }

    companion object {
        private const val DELAY_BETWEEN_MESSAGE = 200L
    }
}
