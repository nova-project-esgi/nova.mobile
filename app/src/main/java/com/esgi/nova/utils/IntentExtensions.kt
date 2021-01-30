package com.esgi.nova.utils

import android.content.Intent
import android.os.Bundle
import java.util.*


fun Intent.clear() {
    replaceExtras(Bundle())
    action = ""
    data = null
    flags = 0
}

fun Intent.putUUIDExtra(key: String, uuid: UUID) = putExtra(key, uuid.toString())


fun Intent.getUUIDExtra(key: String): UUID? =
    getStringExtra(key)?.let { uuid -> UUID.fromString(uuid) }