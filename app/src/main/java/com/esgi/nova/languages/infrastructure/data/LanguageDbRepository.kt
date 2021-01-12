package com.esgi.nova.languages.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.languages.infrastructure.data.Language
import com.esgi.nova.languages.infrastructure.data.LanguageDAO
import java.util.*
import javax.inject.Inject

class LanguageDbRepository @Inject constructor(private val db: AppDatabase):
    BaseRepository<UUID, Language>() {

    override val dao: LanguageDAO
        get() = db.languageDAO()

}