package com.esgi.nova.games.ui.game.fragments

import com.esgi.nova.events.ports.IDetailedChoice

interface OnChoiceConfirmedListener {
    fun onChoiceConfirmed(choice: IDetailedChoice)
}