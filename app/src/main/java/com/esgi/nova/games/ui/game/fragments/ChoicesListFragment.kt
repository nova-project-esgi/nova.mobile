package com.esgi.nova.games.ui.game.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.esgi.nova.databinding.ChoicesListFragmentBinding
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.ui.game.adapters.ChoiceAdapter
import com.esgi.nova.games.ui.game.view_models.GameViewModel
import org.jetbrains.anko.support.v4.runOnUiThread

class ChoicesListFragment : Fragment(), OnChoiceClicked {

    companion object {
        fun newInstance() =
            ChoicesListFragment()
    }

    private var _binding: ChoicesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChoicesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.newChoices.observe(viewLifecycleOwner) { choices -> updateChoices(choices) }
        initChoicesList()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateChoices(choices: List<IDetailedChoice>) {
        runOnUiThread {
            viewModel.updateChoices(choices)
            binding.choicesRv.adapter?.notifyDataSetChanged()
        }
    }

    private fun initChoicesList() {
        binding.choicesRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ChoiceAdapter(viewModel.choices, this@ChoicesListFragment)
        }
    }

    override fun clicked(choice: IDetailedChoice) {
        viewModel.select(choice)
    }

}

