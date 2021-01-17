package com.esgi.nova

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.events.ports.IDetailedChoice

class ChoicesListViewModel : ViewModel() {
    val selected = MutableLiveData<IDetailedChoice>()
    val choices : MutableList<IDetailedChoice> = mutableListOf()

    fun select(item: IDetailedChoice) {
        selected.value = item
    }

}