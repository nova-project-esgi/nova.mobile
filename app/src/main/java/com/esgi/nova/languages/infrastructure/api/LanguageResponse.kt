package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.languages.ports.ILanguage
import java.util.*

data class LanguageResponse(override val id: UUID, override val code: String, override val subCode: String?, val isDefault: Boolean): ILanguage{

}
