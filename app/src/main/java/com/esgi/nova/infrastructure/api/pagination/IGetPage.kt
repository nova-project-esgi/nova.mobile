package com.esgi.nova.infrastructure.api.pagination

//fun interface IGetPage<out T> {
//    suspend fun  get(page: Int?, size: Int?): PageMetadata<T>?
//}

typealias IGetPage<T> = suspend (page: Int?, size: Int?) -> PageMetadata<T>