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
    fun loadNext(): MutableSet<T>
    fun loadPrevious(): MutableSet<T>
    fun loadCurrent(): MutableSet<T>
    fun resetPage()
    fun copy(cursor: IPageCursor<T>)
}