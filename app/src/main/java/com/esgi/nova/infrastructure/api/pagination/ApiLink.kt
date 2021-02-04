package com.esgi.nova.infrastructure.api.pagination

class ApiLink(
    override val rel: ILink.Relation,
    override val href: String,
    override val method: String
) : ILink {
    fun toSecuredLink() = SecuredLink(rel, href, method)
}