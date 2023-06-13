package com.abdulaziz.gallaryapp.ui.util

import android.os.Environment
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class FilePathHandlerTest {
    private lateinit var filePathHandler: FilePathHandler

    @Before
    fun setUp() {
        filePathHandler = FilePathHandler()
    }

    @Test
    fun `getThumbnail throws exception when path is empty`() {
        val emptyPath = ""
        assertThrows(IllegalStateException::class.java) {
            filePathHandler.getThumbNail(emptyPath)
        }.let { exception ->
            assertEquals("Path is empty", exception.message)
        }
    }

    @Test
    fun `getThumbnail throws exception when folder does not exist`() {
        val nonExistentPath = "/path/to/nonexistent/folder"

        assertThrows(IllegalStateException::class.java) {
            filePathHandler.getThumbNail(nonExistentPath)
        }.let { exception ->
            assertEquals("Folder does not exist", exception.message)
        }
    }


}