package com.esgi.nova.resources.application

import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDbRepository
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.resources.ports.IResource
import java.util.*
import javax.inject.Inject

class GetImageResourceWrappersByDifficultyId @Inject constructor(
    private val resourceDbRepository: DifficultyResourceDbRepository,
    private val getFileBitmapById: GetFileBitmapById
) {
    fun execute(difficultyId: UUID):List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>{
        return resourceDbRepository.getDifficultyResourcesByDifficultyId(difficultyId).mapNotNull {startValueResource ->
            getFileBitmapById.execute(FsConstants.Paths.Resources, startValueResource.id)?.let { img ->
                return@mapNotNull FileWrapper(startValueResource, img)
            }
        }
    }
}