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


    fun getSelectedLanguage(): IAppLanguage?{
        return dao.getSelectedLanguage().firstOrNull()
    }

    fun selectLanguage(id: UUID){
        dao.getById(id).firstOrNull()?.let { language ->
            language.isSelected = true
            update(language )
        }
    }

    fun deselectLanguages(): List<UUID> {
        return dao.getSelectedLanguage().map {language ->
            language.isSelected = false
            dao.update(language)
            return@map language.id
        }
    }


}