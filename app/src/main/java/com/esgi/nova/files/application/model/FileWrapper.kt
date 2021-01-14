package com.esgi.nova.files.application.model

import android.graphics.Bitmap
import com.esgi.nova.files.infrastructure.ports.IFileWrapper

data class FileWrapper<Entity> (override val data: Entity, override val img: Bitmap): IFileWrapper<Entity>{}