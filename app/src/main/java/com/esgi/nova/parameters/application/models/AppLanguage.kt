package com.esgi.nova.parameters.application.models

import com.esgi.nova.languages.ports.IAppLanguage
import java.util.*

data class AppLanguage(
    override val isSelected: Boolean = false,
    override val id: UUID,
    override val code: String,
    override val subCode: String?
) : IAppLanguage {
}