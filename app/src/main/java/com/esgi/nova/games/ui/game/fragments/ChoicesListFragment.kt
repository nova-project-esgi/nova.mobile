package com.esgi.nova.games.ui.game.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.ui.game.adapters.ChoiceAdapter
import com.esgi.nova.games.ui.game.view_models.ChoicesListViewModel
import kotlinx.android.synthetic.main.choices_list_fragment.*
import org.jetbrains.anko.support.v4.runOnUiThread

class ChoicesListFragment : Fragment(), OnChoiceClicked, Observer<List<IDetailedChoice>> {

    companion object {
        fun newInstance() =
            ChoicesListFragment()
    }

    private val viewModel by activityViewModels<ChoicesListViewModel>()
    private var choices: MutableList<IDetailedChoice> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choices_list_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.choices.value?.let { choices ->
            this.choices = choices.toMutableList()
        }
        initChoicesList()
        viewModel.choices.observe(viewLifecycleOwner, this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initChoicesList() {
        choices_rv?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ChoiceAdapter(choices, this@ChoicesListFragment)
        }
    }

    override fun clicked(choice: IDetailedChoice) {
        viewModel.select(choice)
    }

    override fun onChanged(choicesList: List<IDetailedChoice>?) {
        runOnUiThread {
            choices.clear()
            choices.addAll(choicesList ?: listOf())
            choices_rv.adapter?.notifyDataSetChanged()
        }

    }

}

