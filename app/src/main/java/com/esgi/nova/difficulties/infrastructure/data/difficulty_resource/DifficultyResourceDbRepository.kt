package com.esgi.nova.difficulties.infrastructure.data.difficulty_resource

import com.esgi.nova.difficulties.ports.IDifficultyResource
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.infrastructure.data.repository.BaseRepository
import com.esgi.nova.utils.reflectMapCollection
import com.esgi.nova.utils.reflectMapNotNull
import java.util.*
import javax.inject.Inject

class DifficultyResourceDbRepository @Inject constructor(override val dao: DifficultyResourceDAO) :
    BaseRepository<UUID, DifficultyResourceEntity, IDifficultyResource>() {


    fun getAllDifficultyWithResources(): List<IDetailedDifficulty>{
        val difficultyWithResourceList = dao.getAllDifficultyWithResource()
        val difficultyWithResourcesList = mutableListOf<IDetailedDifficulty>()
        difficultyWithResourceList.forEach {difficultyWithResource ->
            difficultyWithResourcesList
                .firstOrNull { difficultyWithResources -> difficultyWithResource.difficulty.id == difficultyWithResources.id }
                ?.let { difficultyWithResources ->
                    difficultyWithResources.resources += difficultyWithResource.toStartValueResource()
                    return@forEach
                }
            difficultyWithResourcesList += difficultyWithResource.toDifficultyWithResources()
        }
        return difficultyWithResourcesList
    }


    override fun toEntity(el: IDifficultyResource): DifficultyResourceEntity = el.reflectMapNotNull()


    override fun toEntities(entities: Collection<IDifficultyResource>): Collection<DifficultyResourceEntity> = entities.reflectMapCollection()

}