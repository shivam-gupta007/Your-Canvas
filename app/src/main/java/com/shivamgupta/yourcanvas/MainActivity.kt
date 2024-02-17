package com.shivamgupta.yourcanvas

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shivamgupta.yourcanvas.ui.theme.YourCanvasTheme
import com.shivamgupta.yourcanvas.ui.theme.md_theme_light_surface
import com.shivamgupta.yourcanvas.utils.FileUtils
import com.shivamgupta.yourcanvas.utils.GenerateBitmap
import com.shivamgupta.yourcanvas.utils.PermissionUtils
import com.shivamgupta.yourcanvas.utils.toast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourCanvasTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    val defaultBgColor = md_theme_light_surface.toArgb()
    var screenBgColor by rememberSaveable { mutableIntStateOf(defaultBgColor) }
    var shouldShowColorPicker by rememberSaveable { mutableStateOf(false) }
    var pickedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var composableBitmap: () -> Bitmap? = { null }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){
        pickedImageUri = it
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(!isGranted){
            context.toast(text = "Please grant storage permission to save image")
        }
    }

    pickedImageUri?.let {
        screenBgColor = defaultBgColor
    }

    Scaffold(bottomBar = {
        BottomAppBar(
            actions = {
                IconButton(onClick = {
                    shouldShowColorPicker = !shouldShowColorPicker
                }) {
                    Icon(Icons.Outlined.Palette, contentDescription = "choose color")
                }

                /*IconButton(onClick = {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Icon(
                        Icons.Outlined.Image,
                        contentDescription = "pick image",
                    )
                }*/
                IconButton(onClick = {
                    if (PermissionUtils.isStoragePermissionGranted(context)) {
                        composableBitmap.invoke()?.let {
                            FileUtils.saveImage(bitmap = it, onImageSaved = {
                                context.toast(text = "Image saved successfully.")
                            }, onImageFailure = { exception ->
                                Log.d(TAG, "MainScreen: onImageFailure - $exception")
                                context.toast(text = "An error occurred while saving the image.")
                            })
                        }
                    } else {
                        requestPermissionLauncher.launch(PermissionUtils.storagePermission)
                    }
                }) {
                    Icon(
                        Icons.Outlined.FileDownload,
                        contentDescription = "download image",
                    )
                }
            },
        )
    }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = Color(screenBgColor),
        ) {
            composableBitmap = GenerateBitmap {
                WallScreen(
                    backgroundImageUri = pickedImageUri,
                    screenColor = Color(screenBgColor)
                )
            }

            if(shouldShowColorPicker){
                ColorPickerBottomSheet(
                    selectedColor = screenBgColor,
                    onBottomSheetDismissed = {
                        shouldShowColorPicker = false
                    }, onColorPicked = {
                        screenBgColor = it
                        pickedImageUri = null
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    YourCanvasTheme {
        MainScreen()
    }
}

const val TAG = "MainScreen"