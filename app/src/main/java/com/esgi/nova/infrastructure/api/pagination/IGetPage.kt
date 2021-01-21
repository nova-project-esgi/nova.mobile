package com.esgi.nova.infrastructure.api.pagination

fun interface IGetPage<out T> {
    fun  get(page: Int?, size: Int?): PageMetadata<T>?
}
