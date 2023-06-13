package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import com.abdulaziz.gallaryapp.BuildConfig

class PermissionHandler {
    companion object {
        private const val STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
        private const val STORAGE_PERMISSION_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
    }


    fun checkAccessPermission(
        context: Context,
    ): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) STORAGE_PERMISSION_IMAGES else STORAGE_PERMISSION)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>) {
        launcherMultiplePermissions.launch(arrayOf( if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) STORAGE_PERMISSION_IMAGES else STORAGE_PERMISSION))
    }
}