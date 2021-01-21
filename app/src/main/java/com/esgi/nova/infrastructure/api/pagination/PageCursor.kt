package com.esgi.nova.infrastructure.api.pagination

import com.esgi.nova.infrastructure.ports.IPageCursor

class PageCursor<T>(override val size: Int = 0, override var loadFunc: IGetPage<T>? = null) : HashSet<T>(size),
    IPageCursor<T> {


    override val hasNext: Boolean?
        get() = _hasNext

    override val hasPrevious: Boolean?
        get() = _hasPrevious

    override val pageSize: Int get() = _pageSize ?: 0
    override val page: Int get() = this.size.div(pageSize)
    override val nextPage: Int get() = page + 1
    override val previousPage: Int get() = if (page - 1  >= 0) page -1 else 0

    private  var _hasNext: Boolean? = null
    private  var _hasPrevious: Boolean? = null
    private  var _pageSize: Int? = null

    private fun updatePagination(pageMetadata: PageMetadata<T>?){
        this.addAll(pageMetadata?.values ?: listOf())
        _hasNext = pageMetadata?.links?.any { link -> link.rel == Link.Relation.NEXT }
        _hasPrevious = pageMetadata?.links?.any { link -> link.rel == Link.Relation.PREVIOUS }
        _pageSize = pageMetadata?.values?.size ?: 0
    }

    override fun loadNext(): MutableSet<T> {
        val pageMetadata = loadFunc?.get(nextPage, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override fun loadPrevious(): MutableSet<T> {
        val pageMetadata = loadFunc?.get(previousPage, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override fun loadCurrent(): MutableSet<T> {
        val pageMetadata = loadFunc?.get(page, _pageSize)
        updatePagination(pageMetadata)
        return this
    }

    override fun resetPage(){
        clear()
        _hasPrevious = null
        _hasNext = null
        _pageSize = null
    }

}