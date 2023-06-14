package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import com.abdulaziz.gallaryapp.BuildConfig

@Suppress("Requires")
class PermissionHandler {
    companion object {
        private const val STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
        private const val STORAGE_PERMISSION_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
        private const val STORAGE_PERMISSION_VIDEO = android.Manifest.permission.READ_MEDIA_VIDEO
    }

    private val isAPILevel33 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    private val permissionsAndroid13 = arrayOf(STORAGE_PERMISSION_IMAGES, STORAGE_PERMISSION_VIDEO)
    private val permissionsAndroid12Below = arrayOf(STORAGE_PERMISSION)

    private fun getPermission(): Array<String> {
        return if (isAPILevel33) permissionsAndroid13 else permissionsAndroid12Below
    }


    fun checkAccessPermission(
        context: Context,
    ): Boolean {
        getPermission().forEach {
            if (ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    fun requestPermission(
        launcherMultiplePermissions
        : ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
    ) {
        launcherMultiplePermissions.launch(getPermission())
    }
}