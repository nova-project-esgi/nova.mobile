package com.esgi.nova.events.ports

interface IDetailedEvent : IEvent {

    val choices: MutableList<out IDetailedChoice>

}

