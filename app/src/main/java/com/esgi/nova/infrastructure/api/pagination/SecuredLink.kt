package com.esgi.nova.infrastructure.api.pagination

import com.esgi.nova.infrastructure.api.ApiConstants
import com.esgi.nova.utils.toHttps

class SecuredLink(
    override val rel: ILink.Relation,
    url: String,
    override val method: String
) : ILink {
    private val _href = url
    override val href: String get() = if (ApiConstants.SecureNetworkOn) _href.toHttps() else _href
}