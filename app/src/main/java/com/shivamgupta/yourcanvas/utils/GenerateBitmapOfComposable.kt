package com.shivamgupta.yourcanvas.utils

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

@Composable
fun GenerateBitmap(
    content: @Composable () -> Unit
): () -> Bitmap {

    val context = LocalContext.current

    //ComposeView that will take composable as its content
    val composeView = remember {
        ComposeView(context)
    }

    //Adds your composable in the 'composeView' variable
    AndroidView(factory = {
        composeView.apply {
            setContent {
                content.invoke()
            }
        }
    })

    //Callback function which returns the bitmap of the Composable
    fun captureBitmap(): Bitmap {
        return composeView.drawToBitmap()
    }

    //return the callback function
    return ::captureBitmap
}