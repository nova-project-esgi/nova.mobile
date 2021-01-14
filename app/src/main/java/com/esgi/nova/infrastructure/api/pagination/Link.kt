package com.esgi.nova.infrastructure.api.pagination

data class Link(private val rel: String, val href: String, private val method: String)