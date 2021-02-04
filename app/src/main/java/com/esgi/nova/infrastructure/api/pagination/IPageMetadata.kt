package com.esgi.nova.infrastructure.api.pagination

interface IPageMetadata<out T> {
    val links: List<ILink>
    val values: List<T>
    val total: Int
}