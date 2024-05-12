package com.example.globalcustomtoast.toast.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globalcustomtoast.toast.consume.ToastConsumer
import com.example.globalcustomtoast.toast.store.ToastType
import kotlinx.coroutines.delay


@Composable
fun FloatingToastView(toastConsumer: ToastConsumer, modifier: Modifier = Modifier) {
    val toast = toastConsumer.currentToast.collectAsState().value

    LaunchedEffect(toast) {
        if (toast == null) {
            return@LaunchedEffect
        }

        delay(toastConsumer.getToastDuration(toast.duration))
        toastConsumer.onToastDisappear()
    }

    if (toast != null) {
        CustomToast(
            message = toast.message,
            type = toast.type,
            modifier = modifier
        )
    }
}

@Composable
fun CustomToast(
    message: String,
    modifier: Modifier = Modifier,
    @ToastType type: Int = ToastType.SUCCESS
) {
    val backgroundColor = rememberToastBackgroundColor(type)
    val textColor = rememberToastTextColor(type)

    Row(
        modifier = modifier
            .background(
                backgroundColor,
                RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = message,
            color = textColor
        )
    }
}

@Composable
fun rememberToastBackgroundColor(@ToastType type: Int): Color {
    return remember(type) {
        when (type) {
            ToastType.SUCCESS -> Color(0xFF284A16)
            else -> Color(0xFFC74138)
        }
    }
}

@Composable
fun rememberToastTextColor(@ToastType type: Int): Color {
    return remember(type) {
        when (type) {
            ToastType.SUCCESS -> Color(0xFFFBFFF9)
            else -> Color(0xFFFFF0F0)
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
private fun PBToastPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        CustomToast(
            message = "Request failed, please try again",
            type = ToastType.ERROR
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomToast(message = "Request successful")
    }
}
