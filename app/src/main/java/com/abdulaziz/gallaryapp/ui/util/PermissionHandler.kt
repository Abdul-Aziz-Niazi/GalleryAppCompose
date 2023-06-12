package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionHandler {
    companion object {
        private const val STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }


    fun checkAccessPermission(
        context: Context,
    ): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, STORAGE_PERMISSION)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(launcherMultiplePermissions: ManagedActivityResultLauncher<String, Boolean>) {
        launcherMultiplePermissions.launch(STORAGE_PERMISSION)
    }
}