package com.esgi.nova.infrastructure.api

import com.esgi.nova.utils.toHttps
import java.net.URL

class Link(
    href: String,
    val rel: String,
    val method: String
) {
    private val _href: String = href

    val href: String get() = if (ApiConstants.SecureNetworkOn) _href.toHttps() else _href

    val url: URL get() = URL(href)
}