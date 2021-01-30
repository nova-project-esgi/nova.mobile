package com.esgi.nova.events.ports

interface IResumedChoice : IChoice {
    val resources: MutableList<out IChoiceResource>
}