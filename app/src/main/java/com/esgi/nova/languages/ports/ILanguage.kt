package com.esgi.nova.languages.ports

import com.esgi.nova.infrastructure.data.IIdEntity
import java.util.*

interface ILanguage: IIdEntity<UUID> {
    override val id: UUID
    val code: String
    val subCode: String
    val tag: String
        get() {
            return "$code-$subCode"
        }
}

