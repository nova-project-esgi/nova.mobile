package com.esgi.nova.languages.infrastructure.dto

import java.util.*

data class LanguageDto(val id: UUID, val code: String, val subCode: String?, val isDefault: Boolean){

}
