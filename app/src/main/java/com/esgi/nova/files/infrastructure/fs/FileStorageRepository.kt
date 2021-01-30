package com.esgi.nova.files.infrastructure.fs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.esgi.nova.files.infrastructure.ports.IFileStreamResumeWithDestination
import com.esgi.nova.infrastructure.ports.IClear
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

class FileStorageRepository @Inject constructor(@ApplicationContext private val context: Context) :
    IClear {

    private val storageDir get() = context.filesDir


    private fun <T> getFileWithoutExtension(path: String, fileNameWithoutExtension: T): File? {
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

    fun upsertFiles(files: List<IFileStreamResumeWithDestination>) = try {
        files.groupBy { file -> file.destinationDir }.forEach { (destinationDir, filesList) ->

            val fileDir = File("$storageDir$destinationDir")
            fileDir.parentFile?.mkdirs()

            filesList.forEach { file -> saveFile(file) }

            fileDir.listFiles()?.forEach { existingFile ->
                if (!filesList.any { file -> file.fileNameWithoutExtension == existingFile.nameWithoutExtension }) {
                    existingFile.delete()
                }
            }
        }
        true
    } catch (e: java.lang.Exception) {
        Log.e(
            FileStorageRepository::class.java.name,
            "can't upsert files"
        )
        false
    }


    fun saveFile(file: IFileStreamResumeWithDestination): Boolean {
        return try {
            val savedFile =
                File("$storageDir${file.destinationDir}${file.fileNameWithoutExtension}.${file.extension}")
            savedFile.parentFile?.mkdirs()
            if (!savedFile.exists()) {
                if (!savedFile.createNewFile()) {
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
                    Log.i(
                        FileStorageRepository::class.java.name,
                        "file download: $fileSizeDownloaded of $fileSize"
                    )
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

    override fun clear(): Boolean =
        try {
            storageDir.delete()
            true
        } catch (e: IOException) {
            Log.e(
                FileStorageRepository::class.java.name,
                "Can't delete storage dir ${storageDir.path}"
            )
            false
        } catch (e: Exception) {
            false
        }


//    fun saveFile(file: IFileStreamResume, destination: String): Boolean {
//
//        val savedFile = File("$storageDir$destination.${file.extension}")
//        savedFile.parentFile?.mkdirs()
//        if (!savedFile.exists()) {
//            if (!savedFile.createNewFile()) {
//                return false
//            }
//        }
//        val bitmap = BitmapFactory.decodeStream(file.data)
//        val outputStream = FileOutputStream(savedFile)
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
//        outputStream.close()
//        return true
//    }
}