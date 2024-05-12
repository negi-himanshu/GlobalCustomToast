package com.example.globalcustomtoast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.globalcustomtoast.toast.ui.FloatingToastView
import com.example.globalcustomtoast.toast.publish.IToastPublisher
import com.example.globalcustomtoast.toast.consume.ToastConsumer
import com.example.globalcustomtoast.toast.store.ToastData
import com.example.globalcustomtoast.toast.store.ToastDataStore
import com.example.globalcustomtoast.toast.publish.ToastPublisher
import com.example.globalcustomtoast.ui.theme.GlobalCustomToastTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {

    private lateinit var toastConsumer: ToastConsumer
    private lateinit var toastPublisher: IToastPublisher
    private lateinit var scope: CoroutineScope
    private var messageCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scope = CoroutineScope(SupervisorJob())
        toastConsumer = ToastConsumer(ToastDataStore.getToastDataStore(), scope)
        toastPublisher = ToastPublisher(ToastDataStore.getToastDataStore(), scope)

        setContent {
            GlobalCustomToastTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        Button(
                            onClick = { toastPublisher.publish(ToastData("Clicked ${++messageCount} times")) },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text("Send toast message")
                        }
                        FloatingToastView(
                            toastConsumer, modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
