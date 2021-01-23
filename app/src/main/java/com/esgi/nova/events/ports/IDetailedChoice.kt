package com.esgi.nova.events.ports

import java.util.*

interface IDetailedChoice: IChoice{
    val resources: List< IChangeValueResource>

    interface IChangeValueResource {
        val id: UUID
        val name: String
        val changeValue: Int
    }
}