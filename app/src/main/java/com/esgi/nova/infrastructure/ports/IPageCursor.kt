package com.esgi.nova.infrastructure.ports

import com.esgi.nova.infrastructure.api.pagination.IGetPage
import com.esgi.nova.infrastructure.api.pagination.PageMetadata

interface IPageCursor<T>: MutableSet<T> {
    val hasNext: Boolean?
    val hasPrevious: Boolean?
    val pageSize: Int?
    val page: Int
    val nextPage: Int
    val previousPage: Int
    var loadFunc: IGetPage<T>?
    suspend fun loadNext(): MutableSet<T>
    suspend fun loadPrevious(): MutableSet<T>
    suspend fun loadCurrent(): MutableSet<T>
    fun resetPage()
    fun copy(cursor: IPageCursor<T>)
}