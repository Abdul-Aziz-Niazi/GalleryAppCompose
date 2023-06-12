package com.abdulaziz.gallaryapp.ui.util

class FilePathHandler {
    companion object {
        const val ROOT_PATH = "/storage/emulated/0/"
    }
    fun getRootPath(): String {
        return ROOT_PATH
    }

    fun getCameraPath(): String {
        return "${ROOT_PATH}DCIM/Camera/"
    }
}