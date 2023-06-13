package com.abdulaziz.gallaryapp.ui.destinations

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdulaziz.gallaryapp.R
import com.abdulaziz.gallaryapp.ui.destinations.destinations.GalleryMainDestination
import com.abdulaziz.gallaryapp.ui.theme.AppStyles
import com.abdulaziz.gallaryapp.ui.util.PermissionHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun Permission(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val permissionHandler = PermissionHandler()
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->
        val list = permissionMap.filter { !it.value }.keys.toList()
        if (list.isEmpty()) {
            navigateToGallery(navigator)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.text_permission_prompt), style = AppStyles.textSubtitle, modifier = Modifier.padding(bottom = 16.dp))
        Text(
            text = stringResource(id = R.string.text_permission_prompt_message),
            style = AppStyles.textBodySemiBold.copy(textAlign = TextAlign.Center, fontSize = 16.sp)
        )

        Button(onClick = {
            val hasPermission = permissionHandler.checkAccessPermission(context)
            if (hasPermission) {
                navigateToGallery(navigator)
            } else {
                permissionHandler.requestPermission(launcherMultiplePermissions)
            }
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = stringResource(id = R.string.text_permission_prompt_button))
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


