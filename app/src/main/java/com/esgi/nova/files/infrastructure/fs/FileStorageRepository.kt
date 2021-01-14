package com.esgi.nova.files.infrastructure.fs

import android.content.ContentValues.TAG
import android.util.Log
import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

class FileStorageRepository @Inject constructor() {

    fun saveFile(file: IFileStreamResume, destination: String): Boolean{
        return try {
            val savedFile = File("$destination.${file.extension}")
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize: Long = file.fileSize
                var fileSizeDownloaded: Long = 0

                outputStream = FileOutputStream(savedFile)
                while (true) {
                    val read: Int = file.data.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(TAG, "file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                file.data.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }

    }
}