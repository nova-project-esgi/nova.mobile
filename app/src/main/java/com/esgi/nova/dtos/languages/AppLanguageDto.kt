package com.esgi.nova.dtos.languages

import com.esgi.nova.languages.ports.IAppLanguage
import java.util.*

class AppLanguageDto(
    override val isSelected: Boolean,
    override val id: UUID,
    override val code: String,
    override val subCode: String
) : IAppLanguage {
    override fun toString(): String {
            return tag
    }
}