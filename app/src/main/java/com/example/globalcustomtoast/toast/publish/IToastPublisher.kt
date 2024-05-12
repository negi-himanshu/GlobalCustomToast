package com.example.globalcustomtoast.toast.publish

import com.example.globalcustomtoast.toast.store.ToastData

interface IToastPublisher {

    fun publish(data: ToastData)
}