package com.esgi.nova.games.ui.game.fragments

import com.esgi.nova.events.ports.IDetailedChoice

interface OnChoiceClicked {

    fun clicked(choice: IDetailedChoice)
}