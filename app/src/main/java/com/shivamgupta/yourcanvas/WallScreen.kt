package com.shivamgupta.yourcanvas

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

@Composable
fun WallScreen(
    backgroundImageUri: Uri?
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var offsetX by rememberSaveable { mutableFloatStateOf(0F) }
        var offsetY by rememberSaveable { mutableFloatStateOf(0F) }
        var text by rememberSaveable { mutableStateOf("") }
        //var textSize by rememberSaveable{ mutableFloatStateOf(MaterialTheme.typography.headlineSmall.fontSize.value) }

        backgroundImageUri?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = null
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth()
            .offset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }.pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }, contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(all = 4.dp),
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (text.isBlank()) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(alpha = 0.5F),
                                text = "Click here to add text",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                ), textAlign = TextAlign.Center
                            )
                        }
                    }
                    innerTextField.invoke()
                }
            )
        }
    }
}
