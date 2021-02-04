package com.esgi.nova.infrastructure.api.pagination

data class PageMetadata<T>(
    override val links: List<ApiLink>,
    override val values: List<T>,
    override val total: Int
) :
    IPageMetadata<T> {

    fun toSecuredPage() = SecuredPageMetadata(
        links = links.map { it.toSecuredLink() },
        values = values,
        total = total
    )
}

