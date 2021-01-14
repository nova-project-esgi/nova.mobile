package com.esgi.nova.files.infrastructure.ports

import android.graphics.Bitmap

interface IFileWrapper<out Entity> {
    val data: Entity
    val img: Bitmap
}