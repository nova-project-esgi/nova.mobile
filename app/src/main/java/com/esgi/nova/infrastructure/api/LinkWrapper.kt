package com.esgi.nova.infrastructure.api

import com.esgi.nova.infrastructure.api.pagination.ILink

data class LinkWrapper<out Data>(val data: Data, val link: ILink)