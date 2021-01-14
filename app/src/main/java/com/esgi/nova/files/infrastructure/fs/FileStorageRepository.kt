package com.esgi.nova.files.infrastructure.fs

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.esgi.nova.files.infrastructure.ports.IFileStreamResume
import com.esgi.nova.utils.withoutExtension
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.file.Paths
import javax.inject.Inject

class FileStorageRepository @Inject constructor(private val context: Context) {

    private val storageDir get() = context.filesDir


    private fun <T> getFileWithoutExtension(path: String, fileNameWithoutExtension: T): File?{
        val dir = File("$storageDir$path")
        return dir.listFiles()?.firstOrNull { file ->
            file.nameWithoutExtension == fileNameWithoutExtension.toString()
        }
    }


    fun <Id> getBitMapFromPathById(path: String, id: Id): Bitmap? {
        getFileWithoutExtension(path, id)?.let { file ->
            return BitmapFactory.decodeFile(file.path)
        }
        return null
    }


    fun saveFile(file: IFileStreamResume, destination: String): Boolean{
        return try {
            val savedFile = File("$storageDir$destination.${file.extension}")
            savedFile.parentFile?.mkdirs()
            if(!savedFile.exists()){
                if(!savedFile.createNewFile()){
                        return false
                    }
            }
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