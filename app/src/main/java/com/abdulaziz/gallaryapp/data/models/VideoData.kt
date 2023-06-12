package com.abdulaziz.gallaryapp.data.models

data class VideoData(
    override var id: String,
    override var name: String,
    override var path: String,
    override var type: MediaDataTypes = MediaDataTypes.Video,
) : MediaData()
