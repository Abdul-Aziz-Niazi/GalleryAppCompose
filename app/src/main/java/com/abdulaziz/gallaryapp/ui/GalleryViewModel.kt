package com.abdulaziz.gallaryapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulaziz.gallaryapp.data.models.ImageData
import com.abdulaziz.gallaryapp.data.models.MediaData

class GalleryViewModel : ViewModel() {

    val mediaData: LiveData<List<MediaData>>
    get() = _mediaData
    private var _mediaData = MutableLiveData<List<MediaData>>()


    fun setMedia(list: List<MediaData>) {
        _mediaData.value = list
    }
}