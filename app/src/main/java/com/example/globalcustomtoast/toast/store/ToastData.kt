package com.example.globalcustomtoast.toast.store

import androidx.annotation.IntDef


data class ToastData(
    val message: String,
    @ToastType val type: Int = ToastType.SUCCESS,
    @ToastDuration val duration: Int = ToastDuration.SHORT
)

@IntDef(ToastDuration.SHORT, ToastDuration.LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastDuration {
    companion object {
        const val SHORT = 0
        const val LONG = 1
    }
}

@IntDef(ToastType.SUCCESS, ToastType.ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastType {
    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
    }
}

object ToastDurationConstants {
    const val SHORT = 3000L
    const val LONG = 5000L
}
