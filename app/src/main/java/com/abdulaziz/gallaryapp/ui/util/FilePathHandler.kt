package com.abdulaziz.gallaryapp.ui.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.abdulaziz.gallaryapp.data.models.AlbumData
import com.abdulaziz.gallaryapp.data.models.MediaData
import com.abdulaziz.gallaryapp.data.models.ImageData
import com.abdulaziz.gallaryapp.data.models.VideoData
import com.abdulaziz.gallaryapp.data.models.MediaDataTypes
import java.io.File


class FilePathHandler {


    fun getImagesFromPath(activity: Context): List<ImageData> {
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
                        imageId.toString(), getName(filePath), filePath, MediaDataTypes.Image
                    )
                )
            }
            cursor.close()
        }
        return listOfAllImages
    }

    fun getName(path: String): String {
        return path.substring(path.lastIndexOf('/') + 1, path.length)
    }

    fun getAlbums(context: Context): ArrayList<AlbumData> {
        val albums = arrayListOf<AlbumData>()
        val counter = arrayListOf<String>()
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.RELATIVE_PATH),
            null,
            null,
            null
        )

        while (cursor?.moveToNext() == true) {
            counter.add(cursor.getString(0) + "," + cursor.getString(1))
        }
        cursor?.close()

        val videoCursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.VideoColumns.RELATIVE_PATH),
            null,
            null,
            null
        )
        while (videoCursor?.moveToNext() == true) {
            counter.add(videoCursor.getString(0) + "," + videoCursor.getString(1))
        }
        videoCursor?.close()

        counter.groupingBy { it }.eachCount().forEach {
            val pathSplit = it.key.split(",")
            albums.add(
                AlbumData(
                    pathSplit.first(),
                    pathSplit.first(),
                    Environment.getExternalStorageDirectory().absolutePath + "/" + pathSplit[1],
                    it.value
                )
            )
        }

        return albums
    }

    fun getThumbNail(path: String): File {
        check(path.isNotEmpty()) { "Path is empty" }
        val folder = File(path.ifEmpty { throw IllegalArgumentException("Path is empty") })
        check(folder.exists()) { "Folder does not exist" }
        val file = folder.listFiles()?.find { it.isFile && isMediaFile(it.absolutePath) }
        return file ?: error("No media file found")
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

    fun getVideosFromPath(context: Context): List<VideoData> {
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
                        videoId.toString(), getName(filePath), filePath, MediaDataTypes.Video
                    )
                )
            }
            cursor.close()
        }
        return listOfAllVideos
    }


    fun getListsOfImagesAndVideos(context: Context, path: String): List<MediaData> {
        val allMedia = arrayListOf<MediaData>()
        when (path) {
            "images/" -> {
                allMedia.addAll(getImagesFromPath(context))
            }
            "videos/" -> {
                allMedia.addAll(getVideosFromPath(context))
            }
            else -> {
                allMedia.addAll(getImagesFromPath(context).filter { it.path.contains(path) })
                allMedia.addAll(getVideosFromPath(context).filter { it.path.contains(path) })
            }
        }

        return allMedia
    }
}