package com.esgi.nova

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.esgi.nova.adapters.ChoiceAdapter
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.choices_list_fragment.*

class ChoicesListFragment : Fragment() {

    companion object {
        fun newInstance() = ChoicesListFragment()
    }

    private  val viewModel by activityViewModels<ChoicesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choices_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        choicesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ChoiceAdapter(viewModel.choices)
        }
    }

}