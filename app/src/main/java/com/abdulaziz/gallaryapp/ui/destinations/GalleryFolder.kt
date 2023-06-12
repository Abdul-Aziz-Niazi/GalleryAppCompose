package com.abdulaziz.gallaryapp.ui.destinations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.abdulaziz.gallaryapp.R
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun GalleryFolder(navigator: DestinationsNavigator) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(100) { index ->
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { }
                    .background(Color.LightGray),
            )
        }
    }
}