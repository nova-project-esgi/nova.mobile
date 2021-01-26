package com.esgi.nova.infrastructure.api.pagination

import com.esgi.nova.infrastructure.ports.IPageCursor
import java.util.*

class PageCursor<T>(override var loadFunc: IGetPage<T>? = null, comparator: Comparator<T>? = null) : TreeSet<T>(comparator),
    IPageCursor<T> {



    override val hasNext: Boolean?
        get() = _hasNext

    override val hasPrevious: Boolean?
        get() = _hasPrevious

    override val pageSize: Int? get() = _pageSize

    override val page: Int get() {
        pageSize?.let {
            if (it != 0) {
                return size / it - 1
            }
        }
        return 0
    }
    override val nextPage: Int get() = page + 1
    override val previousPage: Int get() = if (page - 1  >= 0) page -1 else 0

    private  var _hasNext: Boolean? = null
    private  var _hasPrevious: Boolean? = null
    private  var _pageSize: Int? = null

    private fun updatePagination(pageMetadata: PageMetadata<T>?){
        this.addAll(pageMetadata?.values ?: listOf())
        _hasNext = pageMetadata?.links?.any { link -> link.rel == Link.Relation.NEXT }
        _hasPrevious = pageMetadata?.links?.any { link -> link.rel == Link.Relation.PREVIOUS }
        if(_pageSize == null){
            _pageSize = pageMetadata?.values?.size
        }
    }

    override suspend fun loadNext(): MutableSet<T> {
        val pageMetadata = loadFunc?.invoke(nextPage, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override suspend fun loadPrevious(): MutableSet<T> {
        val pageMetadata = loadFunc?.invoke(previousPage, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override suspend fun loadCurrent(): MutableSet<T> {
        val pageMetadata = loadFunc?.invoke(page, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override fun resetPage(){
        clear()
        _hasPrevious = null
        _hasNext = null
        _pageSize = null
    }

    override fun copy(cursor: IPageCursor<T>) {
        loadFunc = cursor.loadFunc
        this.clear()
        this.addAll(cursor)
        _hasPrevious = cursor.hasPrevious
        _pageSize = cursor.pageSize
        _hasNext = cursor.hasNext
    }

}