package com.abdulaziz.gallaryapp.ui.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import com.abdulaziz.gallaryapp.data.models.ImageData
import com.abdulaziz.gallaryapp.data.models.MediaDataTypes
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File


class FilePathHandlerTest {
    private lateinit var contextMock: Context
    private lateinit var contentResolverMock: ContentResolver
    private lateinit var cursorMock: Cursor
    private lateinit var filePathHandler: FilePathHandler

    @Before
    fun setUp() {
        contextMock = mockk()
        contentResolverMock = mockk()
        cursorMock = mockk()
        every { contextMock.contentResolver } returns contentResolverMock
        filePathHandler = FilePathHandler()
    }

    @Test
    fun getImagesFromPath_returns_empty_list_when_cursor_is_null() {
        every { contentResolverMock.query(any(), any(), any(), any(), any()) } returns null

        val result = filePathHandler.getImagesFromPath(contextMock)

        assertEquals(emptyList<ImageData>(), result)
    }

    @Test
    fun getImagesFromPath_returns_list_of_ImageData() {
        val imageId = 1L
        val filePath = "/path/to/image.jpg"
        val expectedImageData = ImageData(
            imageId.toString(),
            filePathHandler.getName(filePath),
            filePath,
            MediaDataTypes.Image
        )
        every { contextMock.contentResolver } returns contentResolverMock
        every { contentResolverMock.query(any(), any(), any(), any(), any()) } returns cursorMock
        every { cursorMock.getColumnIndexOrThrow(any()) } returns 0
        every { cursorMock.moveToNext() } returns true andThen false
        every { cursorMock.getLong(any()) } returns imageId
        every { cursorMock.getString(any()) } returns filePath
        every { cursorMock.close() } returns Unit

        val result = filePathHandler.getImagesFromPath(contextMock)

        assertEquals(listOf(expectedImageData), result)
    }

}