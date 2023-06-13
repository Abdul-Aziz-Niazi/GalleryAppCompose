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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdulaziz.gallaryapp.MainActivity
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.abdulaziz.gallaryapp.ui.util.FilePathHandler
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun GalleryFolder(navigator: DestinationsNavigator, path: String) {
    val context = LocalContext.current
    val filePathHandler = FilePathHandler()
    val viewModel: GalleryViewModel = viewModel(LocalContext.current as MainActivity)
    viewModel.getMedia(context, path)
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(viewModel.mediaData.value.orEmpty()) { item ->
            GlideImage(
                model = File(item.path),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { }
                    .background(Color.LightGray),
            ) {
                it.apply {
                    override(150, 150).submit()
                }
            }
        }
    }
}