package com.abdulaziz.gallaryapp.ui.destinations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdulaziz.gallaryapp.R
import com.abdulaziz.gallaryapp.ui.destinations.destinations.GalleryMainDestination
import com.abdulaziz.gallaryapp.ui.destinations.destinations.PermissionDestination
import com.abdulaziz.gallaryapp.ui.theme.AppStyles
import com.abdulaziz.gallaryapp.ui.theme.Green700
import com.abdulaziz.gallaryapp.ui.util.PermissionHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination(start = true)
@Composable
fun Splash(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val permissionHandler = PermissionHandler()
    LaunchedEffect(Unit) {
        delay(1500)
        if (permissionHandler.checkAccessPermission(context)) {
            navigateToGallery(navigator)
        } else {
            navigateToPermissionPrompt(navigator)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.gallery), contentDescription = "Gallery")
            Text(
                text = "Gallery", color = Green700, modifier = Modifier
                    .padding(16.dp),
                style = AppStyles.textTitle
            )
        }
    }
}

private fun navigateToPermissionPrompt(navigator: DestinationsNavigator) {
    navigator.navigate(PermissionDestination.route) {
        popUpTo(NavGraphs.root.route) {
            inclusive = true
        }
    }
}

private fun navigateToGallery(navigator: DestinationsNavigator) {
    navigator.navigate(GalleryMainDestination.route) {
        popUpTo(NavGraphs.root.route) {
            inclusive = true
        }
    }
}

