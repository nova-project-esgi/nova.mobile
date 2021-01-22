package com.esgi.nova.utils

fun Int.toChangeString() = if (this >= 0) "+${this}" else this.toString()