package com.esgi.nova.games.infrastructure.data.game.models

import com.esgi.nova.events.infrastructure.data.events.EventDbRepository
import javax.inject.Inject


class CanLaunchGame @Inject constructor(
    private val eventDbRepository: EventDbRepository
) {

    fun execute(): Boolean = eventDbRepository.getCount() > 0
}