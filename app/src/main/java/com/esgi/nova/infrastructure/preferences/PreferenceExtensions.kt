package com.esgi.nova.infrastructure.preferences

import android.content.SharedPreferences
import java.util.*

fun SharedPreferences.Editor.putUUID(key: String, uuid: UUID): SharedPreferences.Editor =
    putString(key, uuid.toString())

fun SharedPreferences.getUUID(key: String, defValue: UUID?): UUID? =
    getString(key, defValue?.toString())?.let { uuid -> UUID.fromString(uuid) }