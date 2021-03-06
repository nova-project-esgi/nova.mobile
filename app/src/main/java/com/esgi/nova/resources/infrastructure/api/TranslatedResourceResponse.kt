package com.esgi.nova.resources.infrastructure.api

import com.esgi.nova.infrastructure.api.pagination.ApiLink
import com.esgi.nova.resources.ports.IResource
import java.util.*

data class TranslatedResourceResponse(
    override val id: UUID,
    val language: String,
    override val name: String,
    val iconUrl: ApiLink
) : IResource