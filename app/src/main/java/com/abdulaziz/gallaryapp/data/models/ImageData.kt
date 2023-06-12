package com.abdulaziz.gallaryapp.data.models

data class ImageData(
    override var id: String,
    override var name: String,
    override var path: String,
    override var type: MediaDataTypes = MediaDataTypes.Image,
) : MediaData()