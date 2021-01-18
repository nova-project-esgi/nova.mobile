package com.esgi.nova.games.ui.fragments

import com.esgi.nova.events.ports.IDetailedChoice

interface OnChoiceClicked {

    fun clicked(choice: IDetailedChoice)
}