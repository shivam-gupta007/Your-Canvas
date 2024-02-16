@file:OptIn(ExperimentalMaterial3Api::class)

package com.shivamgupta.yourcanvas

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import com.shivamgupta.yourcanvas.ui.theme.YourCanvasTheme
import com.shivamgupta.yourcanvas.ui.theme.md_theme_light_surface
import com.shivamgupta.yourcanvas.utils.GenerateBitmap

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

    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){
        pickedImageUri = it
    }

    pickedImageUri?.let {
        screenBgColor = defaultBgColor
    }

    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = {
                shouldShowColorPicker = !shouldShowColorPicker
            }) {
                Icon(Icons.Outlined.Palette, contentDescription = "Localized description")
            }
            
            IconButton(onClick = {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Icon(
                    Icons.Outlined.Image,
                    contentDescription = "Localized description",
                )
            }
            /*IconButton(onClick = { *//* do something *//* }) {
                Icon(
                    Icons.Outlined.TextFormat,
                    contentDescription = "Localized description",
                )
            }*/
            IconButton(onClick = {

            }) {
                Icon(
                    Icons.Outlined.FileDownload,
                    contentDescription = "Localized description",
                )
            }
        }, floatingActionButton = {
            /*FloatingActionButton(
                onClick = { *//* do something *//* }, containerColor = BottomAppBarDefaults.bottomAppBarFabColor, elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }*/
        })
    }) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(padding),
            color = Color(screenBgColor),
        ) {
            val bitmap = GenerateBitmap {
                WallScreen(
                    backgroundImageUri = pickedImageUri
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