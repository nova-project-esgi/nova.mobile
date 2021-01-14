package com.esgi.nova.infrastructure.api.pagination

data class PageMetadata<T>(private val links: List<Link>, val values: List<T>, private val total: Int)

