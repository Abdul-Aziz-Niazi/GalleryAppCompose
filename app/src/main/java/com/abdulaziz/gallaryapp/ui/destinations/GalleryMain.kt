package com.abdulaziz.gallaryapp.ui.destinations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.abdulaziz.gallaryapp.R
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun GalleryMain(navigator: DestinationsNavigator) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(100) {
            Box(modifier = Modifier.clickable {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier.fillMaxSize(), contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = 0.3f))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Title", style = TextStyle(color = Color.White))
                    Text(text = "99", style = TextStyle(color = Color.White))
                }
            }
        }
    }
}