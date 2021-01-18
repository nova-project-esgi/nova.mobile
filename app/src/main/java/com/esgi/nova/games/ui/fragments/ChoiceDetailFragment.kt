package com.esgi.nova.games.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.esgi.nova.R
import com.esgi.nova.games.ui.view_models.ChoicesListViewModel
import kotlinx.android.synthetic.main.choice_detail_fragment.*

class ChoiceDetailFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(onChoiceConfirmedListener: OnChoiceConfirmedListener) =
            ChoiceDetailFragment().apply {
                this.onChoiceConfirmedListener = onChoiceConfirmedListener
            }
    }

    private var onChoiceConfirmedListener: OnChoiceConfirmedListener? = null
    private val viewModel by activityViewModels<ChoicesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choice_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choice_description_tv?.text = viewModel.selected.value?.description
        cancel_btn.setOnClickListener(this)
        confirm_btn.setOnClickListener(this)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v) {
            confirm_btn -> viewModel.selected.value?.let { choice ->
                onChoiceConfirmedListener?.onChoiceConfirmed(choice)
            }
            cancel_btn -> viewModel.select(null)
        }
    }

}