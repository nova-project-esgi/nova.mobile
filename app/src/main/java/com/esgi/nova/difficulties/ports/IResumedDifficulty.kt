package com.esgi.nova.difficulties.ports

interface IResumedDifficulty :
    IDifficulty {
    val resources: MutableList<IDifficultyResource>
}