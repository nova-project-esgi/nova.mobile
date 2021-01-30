package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.languages.ports.IDefaultLanguage
import java.util.*

data class LanguageResponse(
    override val id: UUID,
    override val code: String,
    override val subCode: String,
    override val isDefault: Boolean
) : IDefaultLanguage
