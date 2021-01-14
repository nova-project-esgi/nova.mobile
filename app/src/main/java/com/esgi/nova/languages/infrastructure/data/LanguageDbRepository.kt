package com.esgi.nova.languages.infrastructure.data

import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.languages.ports.ILanguage
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class LanguageDbRepository @Inject constructor(override val dao: LanguageDAO):
    BaseRepository<UUID, LanguageEntity, ILanguage>() {


    override fun toEntity(el: ILanguage): LanguageEntity {
        return el.reflectMapNotNull()
    }

    override fun toEntities(entities: Collection<ILanguage>): Collection<LanguageEntity> = entities.reflectMapCollection()



}