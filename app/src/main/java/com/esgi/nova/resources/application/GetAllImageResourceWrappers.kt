package com.esgi.nova.resources.application
import com.esgi.nova.files.application.GetFileBitmapById
import com.esgi.nova.files.dtos.FileWrapperDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.infrastructure.fs.FsConstants
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.resources.ports.IResource
import javax.inject.Inject

class GetAllImageResourceWrappers @Inject constructor(
    private val resourceDbRepository: ResourceDbRepository,
    private val getFileBitmapById: GetFileBitmapById
) {

    suspend fun execute(): List<IFileWrapper<IResource>> {
        return resourceDbRepository
            .getAll()
            .mapNotNull { resource ->
                getFileBitmapById.execute(FsConstants.Paths.Resources, resource.id)?.let { img ->
                    return@mapNotNull FileWrapperDto(resource, img)
                }
                return@mapNotNull null
            }.toList()
    }
}