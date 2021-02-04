package com.esgi.nova.utils

fun String.withoutExtension(): String = this.substring(0, this.lastIndexOf('.'))
fun String.toHttps(): String = this.replaceFirst("http://", "https://")
