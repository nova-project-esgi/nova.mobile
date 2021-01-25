package com.esgi.nova.infrastructure.api.pagination

data class PageMetadata<out T>(val links: List<Link>, val values: List<T>, val total: Int)

