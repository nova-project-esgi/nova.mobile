package com.esgi.nova.events.ports

interface IResumedEvent : IEvent {

    val choices: MutableList<out IResumedChoice>

}