package com.esgi.nova.resources.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.dtos.FileWrapperDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.infrastructure.fs.FsConstants
import java.util.*
import javax.inject.Inject

class GetImageStartValueResourceWrappersByDifficultyId @Inject constructor(
    private val resourceDbRepository: DifficultyResourceDbRepository,
    private val getFileBitmapById: GetFileBitmapById
) {
    suspend fun execute(difficultyId: UUID): List<IFileWrapper<IDetailedDifficulty.IStartValueResource>> {
        return resourceDbRepository.getDifficultyResourcesByDifficultyId(difficultyId)
            .mapNotNull { startValueResource ->
                getFileBitmapById.execute(FsConstants.Paths.Resources, startValueResource.id)
                    ?.let { img ->
                        return@mapNotNull FileWrapperDto(startValueResource, img)
                    }
            }
    }
}