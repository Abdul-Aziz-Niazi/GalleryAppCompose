package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.database.Cursor
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.abdulaziz.gallaryapp.data.models.AlbumData
import com.abdulaziz.gallaryapp.data.models.ImageData
import com.abdulaziz.gallaryapp.data.models.MediaDataTypes
import com.abdulaziz.gallaryapp.data.models.VideoData
import java.io.File


class FilePathHandler {
    companion object {
        val ROOT_PATH = Environment.getExternalStorageDirectory().absolutePath + "/"
    }

    fun getImagesFromPath(activity: Context, path: String): List<ImageData> {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexID: Int
        val listOfAllImages: MutableList<ImageData> = mutableListOf()
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATA)
        var imageId: Long
        cursor = activity.contentResolver.query(uriExternal, projection, null, null, null)
        if (cursor != null) {
            columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                imageId = cursor.getLong(columnIndexID)
                val filePath = cursor.getString(1)
                listOfAllImages.add(
                    ImageData(
                        imageId.toString(), getName(filePath),
                        filePath, MediaDataTypes.Image
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
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.RELATIVE_PATH),
            null, null, null
        )

        while (cursor?.moveToNext() == true) {
            counter.add(cursor.getString(0) + "," + cursor.getString(1))
        }
        cursor?.close()

        val videoCursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.VideoColumns.RELATIVE_PATH),
            null, null, null
        )
        while (videoCursor?.moveToNext() == true) {
            counter.add(videoCursor.getString(0) + "," + videoCursor.getString(1))
        }
        videoCursor?.close()

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

    fun getImageFor(context: Context, path: String): ImageBitmap {
        val file = File(path)
        val uri = Uri.fromFile(file)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (isImageFile(file.absolutePath)) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source).asImageBitmap()
            } else {
                ThumbnailUtils.createVideoThumbnail(file.absolutePath, MediaStore.Images.Thumbnails.MINI_KIND)?.asImageBitmap()!!
            }
        } else {
            if (isImageFile(file.absolutePath)) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri).asImageBitmap()
            } else {
                ThumbnailUtils.createVideoThumbnail(file.absolutePath, MediaStore.Images.Thumbnails.MINI_KIND)?.asImageBitmap()!!
            }
        }
    }

    fun getThumbNail(context: Context, path: String): ImageBitmap {
        if (path.isEmpty()) throw IllegalStateException("Path is empty")
        val folder = File(path)
        if (folder.exists().not()) throw IllegalStateException("Folder does not exist")
        val firstImage = folder.listFiles()?.find { it.isFile && isMediaFile(it.absolutePath) }
            ?: throw IllegalStateException("No media found")

        val uri = Uri.fromFile(firstImage)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (isImageFile(firstImage.absolutePath)) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ThumbnailUtils.extractThumbnail(ImageDecoder.decodeBitmap(source), 100, 100).asImageBitmap()
            } else {
                ThumbnailUtils.createVideoThumbnail(firstImage.absolutePath, MediaStore.Images.Thumbnails.MINI_KIND)?.asImageBitmap()!!
            }
        } else {
            if (isImageFile(firstImage.absolutePath)) {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                ThumbnailUtils.extractThumbnail(bitmap, 100, 100).asImageBitmap()
            } else {
                ThumbnailUtils.createVideoThumbnail(firstImage.absolutePath, MediaStore.Images.Thumbnails.MINI_KIND)?.asImageBitmap()!!
            }
        }
    }

    fun isMediaFile(path: String): Boolean {
        return isImageFile(path) || isVideoFile(path)
    }

    fun isImageFile(path: String): Boolean {
        return path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".jpeg") || path.endsWith(".gif")
    }

    fun isVideoFile(path: String): Boolean {
        return path.endsWith(".mp4") || path.endsWith(".mov") || path.endsWith(".avi") || path.endsWith(".mkv")
    }

    fun getVideosFromPath(context: Context, path: String): List<VideoData> {
        val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexID: Int
        val listOfAllVideos: MutableList<VideoData> = mutableListOf()
        val projection = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.VideoColumns.DATA)
        var videoId: Long
        cursor = context.contentResolver.query(uriExternal, projection, null, null, null)
        if (cursor != null) {
            columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while (cursor.moveToNext()) {
                videoId = cursor.getLong(columnIndexID)
                val filePath = cursor.getString(1)
                listOfAllVideos.add(
                    VideoData(
                        videoId.toString(), getName(filePath),
                        filePath, MediaDataTypes.Video
                    )
                )
            }
            cursor.close()
        }
        return listOfAllVideos.filter { it.path.contains(path) }
    }
}