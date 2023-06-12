package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.database.Cursor
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.abdulaziz.gallaryapp.data.models.AlbumData
import com.abdulaziz.gallaryapp.data.models.ImageData
import java.io.File


class FilePathHandler {
    companion object {
        val ROOT_PATH = Environment.getExternalStorageDirectory().absolutePath + "/"
    }

    fun getImagesFromPath(activity: Context, path: String): List<ImageData> {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        Log.d("FilePathHandler", "getAllShownImagesPath: $uriExternal")
        val cursor: Cursor?
        val columnIndexID: Int
        val listOfAllImages: MutableList<ImageData> = mutableListOf()
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATA)
        var imageId: Long
        cursor = activity.contentResolver.query(uriExternal, projection, null, null, null)
        if (cursor != null) {
            columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                Log.d("FilePathHandler", "getAllShownImagesPath: " + cursor.getString(1))
                imageId = cursor.getLong(columnIndexID)
                val filePath = cursor.getString(1)
                listOfAllImages.add(
                    ImageData(
                        imageId.toString(), getName(filePath),
                        filePath, 0, ""
                    )
                )
            }
            cursor.close()
        }
        return listOfAllImages.filter { it.path.contains(path) }
    }

    private fun getName(path: String): String {
        return path.substring(path.lastIndexOf('/') + 1, path.length)
    }

    fun getAlbums(context: Context): ArrayList<AlbumData> {
        val albums = arrayListOf<AlbumData>()
        val counter = arrayListOf<String>()
        val cursor: Cursor = MediaStore.Images.Media.query(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.RELATIVE_PATH)
        )

        while (cursor.moveToNext()) {
            counter.add(cursor.getString(0) + "," + cursor.getString(1))
        }

        counter.groupingBy { it }.eachCount().forEach {
            val pathSplit = it.key.split(",")
            albums.add(
                AlbumData(
                    pathSplit.first(), pathSplit.first(), ROOT_PATH + pathSplit[1], it.value
                )
            )
        }

        return albums
    }

    fun getImageFor(context: Context, path:String): ImageBitmap {
        val file = File(path)
        val uri = Uri.fromFile(file)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri).asImageBitmap()
        }
    }

    fun getThumbNail(context: Context, path: String): ImageBitmap {
        if (path.isEmpty()) throw IllegalStateException("Path is empty")
        val folder = File(path)
        if (folder.exists().not()) throw IllegalStateException("Folder does not exist")
        val firstImage = folder.listFiles()?.find { it.isFile && (it.extension == "jpg" || it.extension == "png") }
            ?: throw IllegalStateException("No image found")

        val uri = Uri.fromFile(firstImage)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ThumbnailUtils.extractThumbnail(ImageDecoder.decodeBitmap(source), 100, 100).asImageBitmap()
        } else {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            ThumbnailUtils.extractThumbnail(bitmap, 100, 100).asImageBitmap()
        }
    }
}