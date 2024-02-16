package com.shivamgupta.yourcanvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPickerDialog(
    shouldShowDialog: Boolean = true,
    onColorPicker: (Int) -> Unit
){
    val defaultBgColor = MaterialTheme.colorScheme.surface.toArgb()
    var showDialog by rememberSaveable { mutableStateOf(shouldShowDialog) }
    val colorPickerController: ColorPickerController = rememberColorPickerController()
    var pickerColor by rememberSaveable { mutableIntStateOf(defaultBgColor) }

    if(showDialog){
        Dialog(
            onDismissRequest = { showDialog = false}
        ){
            Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    modifier = Modifier.fillMaxWidth()
                        .height(200.dp),
                    controller = colorPickerController,
                    onColorChanged = {
                        pickerColor = it.color.toArgb()
                        onColorPicker(pickerColor)
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            showDialog = false
                            onColorPicker(pickerColor)
                        }
                    ){
                        Text(
                            text = "Apply"
                        )
                    }

                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ){
                        Text(
                            text = "Cancel"
                        )
                    }
                }
            }
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun ColorPickerDialogPreivew(){
    ColorPickerDialog(
        onColorPicker = {}
    )
}