package com.esgi.nova.events.infrastructure.data.choice_resource

import androidx.room.Embedded
import androidx.room.Relation
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity
import com.esgi.nova.resources.infrastructure.data.ResourceEntity

class ChoiceWithResource(

) {
    @Embedded
    lateinit var choiceResource: ChoiceResourceEntity

    @Relation(
        entity = ChoiceEntity::class,
        parentColumn = "choice_id",
        entityColumn = "id"
    )
    lateinit var choices: Set<ChoiceEntity>

    @Relation(
        entity = ResourceEntity::class,
        parentColumn = "resource_id",
        entityColumn = "id"
    )
    lateinit var resources: Set<ResourceEntity>

    val changeValue: Int get() = choiceResource.changeValue
    val choice: ChoiceEntity get() =  choices.first()
    val resource: ResourceEntity get() = resources.first()
}