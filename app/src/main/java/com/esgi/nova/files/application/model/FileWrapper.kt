package com.esgi.nova.files.application.model

import android.graphics.Bitmap

data class FileWrapper<Entity> (val data: Entity, val img: Bitmap){}