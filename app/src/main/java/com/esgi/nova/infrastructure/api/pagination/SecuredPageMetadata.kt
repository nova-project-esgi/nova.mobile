package com.esgi.nova.infrastructure.api.pagination

class SecuredPageMetadata<T>(
    override val links: List<SecuredLink>,
    override val values: List<T>,
    override val total: Int
) : IPageMetadata<T> {
}