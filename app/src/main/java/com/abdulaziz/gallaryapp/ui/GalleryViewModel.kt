package com.abdulaziz.gallaryapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulaziz.gallaryapp.data.models.AlbumData
import com.abdulaziz.gallaryapp.data.models.MediaData
import com.abdulaziz.gallaryapp.ui.util.FilePathHandler
import java.io.File

class GalleryViewModel : ViewModel() {
    private val filePathHandler = FilePathHandler()

    private val thumbnailMap = mutableMapOf<String, File>()

    val allVideos: LiveData<List<MediaData>>
        get() = _allVideos
    private var _allVideos = MutableLiveData<List<MediaData>>()

    val allImages: LiveData<List<MediaData>>
        get() = _allImages
    private var _allImages = MutableLiveData<List<MediaData>>()

    val mediaData: LiveData<List<MediaData>>
        get() = _mediaData
    private var _mediaData = MutableLiveData<List<MediaData>>()


    val albumData: LiveData<List<AlbumData>>
        get() = _albumData
    private var _albumData = MutableLiveData<List<AlbumData>>()

    private fun getAllVideos(context: Context) {
        _allVideos.value = filePathHandler.getVideosFromPath(context)
    }

    private fun getAllImages(context: Context) {
        _allImages.value = filePathHandler.getImagesFromPath(context)
    }

    fun getVideoThumbnail(): String? {
        return allVideos.value?.first()?.path
    }

    fun getImageThumbnail(): String? {
        return allImages.value?.first()?.path
    }

    fun getMedia(context: Context, albumName: String) {
        _mediaData.value = filePathHandler.getListsOfImagesAndVideos(context, albumName)
    }

    fun getAlbums(context: Context) {
        getAllVideos(context)
        getAllImages(context)
        _albumData.value = mutableListOf<AlbumData>().apply {
            addAll(getDefaultAlbums())
            addAll(filePathHandler.getAlbums(context))
        }
    }

    private fun getDefaultAlbums(): ArrayList<AlbumData> {
        val albums = arrayListOf<AlbumData>()
        if (allImages.value.isNullOrEmpty().not()) {
            albums.add(
                AlbumData(
                    "All Images", "All Images", "images/", allImages.value?.size ?: 0
                )
            )
        }
        if (allVideos.value.isNullOrEmpty().not()) {
            albums.add(
                AlbumData(
                    "All Videos", "All Videos", "videos/", allVideos.value?.size ?: 0
                )
            )
        }

        return albums
    }

    fun getThumbNail(path: String): File {
        val thumbnail = thumbnailMap.getOrElse(path) {
            filePathHandler.getThumbNail(path).run {
                thumbnailMap[path] = this
                this
            }
        }
        return thumbnail
    }
}