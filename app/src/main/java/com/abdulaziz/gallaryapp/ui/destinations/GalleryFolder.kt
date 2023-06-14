package com.abdulaziz.gallaryapp.ui.destinations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdulaziz.gallaryapp.MainActivity
import com.abdulaziz.gallaryapp.ui.GalleryViewModel
import com.abdulaziz.gallaryapp.ui.util.FilePathHandler
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File
import com.abdulaziz.gallaryapp.R
import com.abdulaziz.gallaryapp.data.models.MediaDataTypes

@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun GalleryFolder(navigator: DestinationsNavigator, path: String) {
    val defaultItemSize = 150
    val defaultSpanSize = 3
    val context = LocalContext.current
    val viewModel: GalleryViewModel = viewModel(LocalContext.current as MainActivity)
    viewModel.getMedia(context, path)
    LazyVerticalGrid(columns = GridCells.Fixed(defaultSpanSize), modifier = Modifier.fillMaxSize()) {
        items(viewModel.mediaData.value.orEmpty()) { item ->
            Box(modifier = Modifier.size(defaultItemSize.dp)) {
                GlideImage(
                    model = File(item.path),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.LightGray).align(Alignment.Center),
                ) {
                    it.apply {
                        override(defaultItemSize).submit()
                    }
                }
                if (item.type == MediaDataTypes.Video) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_video),
                        contentDescription = "videoIcon",
                        modifier = Modifier.align(Alignment.Center).size(20.dp).zIndex(1000f)
                    )
                }

            }
        }
    }
}