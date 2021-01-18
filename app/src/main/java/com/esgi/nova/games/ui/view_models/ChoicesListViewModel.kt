package com.esgi.nova.games.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.events.ports.IDetailedChoice

class ChoicesListViewModel : ViewModel() {
    val selected = MutableLiveData<IDetailedChoice?>()
    val choices = MutableLiveData<List<IDetailedChoice>>()

    fun select(item: IDetailedChoice?) {
        selected.value = item
    }
    fun setChoices(choices: List<IDetailedChoice>){
        this.choices.value = choices
    }

}