package com.esgi.nova.infrastructure.api.pagination


typealias IGetPage<T> = suspend (page: Int?, size: Int?) -> IPageMetadata<T>