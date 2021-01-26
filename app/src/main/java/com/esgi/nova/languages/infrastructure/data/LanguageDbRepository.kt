package com.esgi.nova.languages.infrastructure.data

import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.languages.ports.ILanguage
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class LanguageDbRepository @Inject constructor(override val dao: LanguageDAO):
    BaseRepository<UUID, LanguageEntity, IAppLanguage>() {


    override fun toEntity(el: IAppLanguage): LanguageEntity {
        return el.reflectMapNotNull()
    }

    override fun toEntities(entities: Collection<IAppLanguage>): Collection<LanguageEntity> = entities.reflectMapCollection()


    suspend fun getSelectedLanguage(): IAppLanguage?{
        return dao.getSelectedLanguage().firstOrNull()
    }

    suspend fun selectLanguage(id: UUID){
        dao.getById(id).firstOrNull()?.let { language ->
            language.isSelected = true
            update(language )
        }
    }

    suspend fun deselectLanguages(): List<UUID> {
        return dao.getSelectedLanguage().map {language ->
            language.isSelected = false
            dao.update(language)
            return@map language.id
        }
    }


}