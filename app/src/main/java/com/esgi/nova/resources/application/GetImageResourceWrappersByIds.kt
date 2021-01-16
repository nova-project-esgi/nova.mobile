package com.esgi.nova.resources.application

import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.resources.ports.IResource
import java.util.*
import javax.inject.Inject

class GetImageResourceWrappersByIds @Inject constructor(
    private val resourceDbRepository: ResourceDbRepository,
    private val getFileBitmapById: GetFileBitmapById
) {
    fun execute(resources: MutableList<IDetailedDifficulty.IStartValueResource>):List<IFileWrapper<IResource>>{
        return resourceDbRepository
            .loadAllByIds(resources.map { resource -> resource.id})
            .mapNotNull { resource ->
                getFileBitmapById.execute(FsConstants.Paths.Resources, resource.id)?.let { img ->
                    return@mapNotNull FileWrapper(resource, img)
                }
                return@mapNotNull null
            }.toList()
    }
}