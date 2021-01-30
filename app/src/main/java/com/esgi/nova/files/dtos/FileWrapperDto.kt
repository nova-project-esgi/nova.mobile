package com.esgi.nova.files.dtos

import android.graphics.Bitmap
import com.esgi.nova.files.infrastructure.ports.IFileWrapper

data class FileWrapperDto<Entity>(override val data: Entity, override val file: Bitmap) :
    IFileWrapper<Entity>