package com.abdulaziz.gallaryapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulaziz.gallaryapp.data.models.ImageData

class GalleryViewModel : ViewModel() {

    val imageLiveData: LiveData<List<ImageData>>
    get() = _imageLiveData
    private var _imageLiveData = MutableLiveData<List<ImageData>>()


    fun setImages(list: List<ImageData>) {
        _imageLiveData.value = list
    }
}