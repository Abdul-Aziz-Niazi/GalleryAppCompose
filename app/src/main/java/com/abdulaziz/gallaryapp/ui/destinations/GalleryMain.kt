package com.abdulaziz.gallaryapp.ui.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdulaziz.gallaryapp.MainActivity
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.abdulaziz.gallaryapp.ui.destinations.destinations.GalleryFolderDestination
import com.abdulaziz.gallaryapp.ui.util.FilePathHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@Composable
fun GalleryMain(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val filePathHandler = FilePathHandler()
    val viewModel: GalleryViewModel = viewModel(LocalContext.current as MainActivity)
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val albumData = filePathHandler.getAlbums(context)
        items(items = albumData) { item ->
            Box(modifier = Modifier.clickable {
                val listOfImages = filePathHandler.getImagesFromPath(context, item.path)
                viewModel.setImages(listOfImages)
                navigator.navigate(GalleryFolderDestination.route)
            }) {
                Image(
                    bitmap = filePathHandler.getThumbNail(context, item.path),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = 0.3f))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.name, style = TextStyle(color = Color.White))
                    Text(text = item.size.toString(), style = TextStyle(color = Color.White))
                }
            }
        }
    }
}