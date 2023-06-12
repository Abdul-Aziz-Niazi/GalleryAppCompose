package com.abdulaziz.gallaryapp.data.models

open class MediaData(
    open var id: String ="",
    open var name: String = "",
    open var path: String = "",
    open var type: MediaDataTypes = MediaDataTypes.Image,
)
sealed class MediaDataTypes {
    object Image : MediaDataTypes()
    object Video : MediaDataTypes()
}