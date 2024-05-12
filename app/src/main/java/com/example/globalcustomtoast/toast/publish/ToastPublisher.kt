package com.example.globalcustomtoast.toast.publish

import com.example.globalcustomtoast.toast.store.ToastData
import com.example.globalcustomtoast.toast.store.ToastDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ToastPublisher(
    private val toastDataStore: ToastDataStore,
    private val scope: CoroutineScope
) : IToastPublisher {

    override fun publish(data: ToastData) {
        scope.launch { toastDataStore.enqueue(data) }
    }
}