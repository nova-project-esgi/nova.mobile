package com.esgi.nova.infrastructure.api

data class LinkWrapper<out Data>(val data: Data, val link: Link) {
}