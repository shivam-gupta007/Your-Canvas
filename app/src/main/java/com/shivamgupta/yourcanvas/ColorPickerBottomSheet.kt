package com.shivamgupta.yourcanvas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shivamgupta.yourcanvas.ui.theme.DustyRose
import com.shivamgupta.yourcanvas.ui.theme.MutedClay
import com.shivamgupta.yourcanvas.ui.theme.PaleMustard
import com.shivamgupta.yourcanvas.ui.theme.SeafoamGreen
import com.shivamgupta.yourcanvas.ui.theme.SkyBlue
import com.shivamgupta.yourcanvas.ui.theme.SoftCoral
import com.shivamgupta.yourcanvas.ui.theme.md_theme_light_surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerBottomSheet(
    padding: Dp = 16.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 2.dp,
    colorBoxSize: Dp = 60.dp,
    onBottomSheetDismissed: () -> Unit,
    selectedColor: Int,
    onColorPicked: (Int) -> Unit
) {
    val selectedBorderColor = MaterialTheme.colorScheme.onSurface
    var pickedBgColor by rememberSaveable { mutableIntStateOf(selectedColor) }

    val colors = listOf(
        md_theme_light_surface,
        MutedClay,
        SoftCoral,
        SkyBlue,
        DustyRose,
        SeafoamGreen,
    )

    ModalBottomSheet(onDismissRequest = {
        onBottomSheetDismissed()
    }) {
        Column(
            modifier = Modifier.padding(
                top = padding, start = padding, end = padding, bottom = padding * 4
            ), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(5),
            ) {
                items(colors){ colorItem ->
                    Box(
                        modifier = Modifier.padding(all = 8.dp)
                            .clip(shape)
                            .size(colorBoxSize)
                            .background(colorItem)
                            .border(
                                width = borderWidth,
                                color = if(colorItem == Color(pickedBgColor)) selectedBorderColor else Color.Transparent,
                                shape = shape
                            ).clickable {
                                pickedBgColor = colorItem.toArgb()
                                onColorPicked(pickedBgColor)
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickerBottomSheetPreview(){
    ColorPickerBottomSheet(
        onBottomSheetDismissed = {},
        onColorPicked = {},
        selectedColor = Color.White.toArgb()
    )
}
