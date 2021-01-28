package com.esgi.nova.games.ui.game.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.esgi.nova.databinding.ChoiceDetailFragmentBinding
import com.esgi.nova.games.ui.game.view_models.GameViewModel

class ChoiceDetailFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            ChoiceDetailFragment()
    }

    private var _binding: ChoiceDetailFragmentBinding? = null
    private val binding get() = _binding!!

    //    private var onChoiceConfirmedListener: OnChoiceConfirmedListener? = null
    private val viewModel by activityViewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChoiceDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.choiceDescriptionTv.text = viewModel.selectedChoice.value?.description
        binding.cancelBtn.setOnClickListener(this)
        binding.confirmBtn.setOnClickListener(this)
        changeButtonsState(true)
    }

    private fun changeButtonsState(isEnabled: Boolean) {
        binding.confirmBtn.isEnabled = isEnabled
        binding.cancelBtn.isEnabled = isEnabled
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.confirmBtn -> {
                viewModel.confirmChoice()
//                viewModel.selected.value?.let { choice ->
//                    onChoiceConfirmedListener?.onChoiceConfirmed(choice)
//                }
                changeButtonsState(false)
            }
            binding.cancelBtn -> {
                viewModel.select(null)
                changeButtonsState(false)
            }
        }
    }

}