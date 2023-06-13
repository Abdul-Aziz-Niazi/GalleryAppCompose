package com.abdulaziz.gallaryapp.ui.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@Composable
fun GalleryMain(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val filePathHandler = FilePathHandler()
    val viewModel: GalleryViewModel = viewModel(LocalContext.current as MainActivity)


    viewModel.getAlbums(context)
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(items = viewModel.albumData.value.orEmpty()) { item ->
            Box(modifier = Modifier.clickable {
                navigator.navigate(GalleryFolderDestination(item.path).route)
            }) {
                GlideImage(
                    contentDescription = "",
                    model = when (item.path) {
                        "images/" -> viewModel.getImageThumbnail()
                        "videos/" -> viewModel.getVideoThumbnail()
                        else -> viewModel.getThumbNail(item.path)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillWidth,
                ) {
                    it.apply {
                        override(200, 200)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .submit()
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = 0.3f))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.name, style = TextStyle(color = Color.White))
                    Text(text = item.size.toString(), style = TextStyle(color = Color.White))
                }
            }
        }
    }
}