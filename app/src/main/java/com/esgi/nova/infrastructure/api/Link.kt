package com.esgi.nova.infrastructure.api

import java.net.URL

data class Link(
    val href: String,
    val rel: String,
    val method: String
) {
    val url: URL get() = URL(href)
}