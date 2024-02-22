package com.example.homework21

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: RecyclerView = view.findViewById(R.id.recyclerView)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        viewModel.getData()
        viewModel.uiState.observe(requireActivity()) {
            when (it) {
                is MyViewModel.UIState.Empty -> Unit
                is MyViewModel.UIState.Result -> {
                    val data = it.responseHeroes
                    if (data.isNotEmpty()) {
                        val myAdapter = RecyclerViewAdapter(data) {
                            val detailsFragment = DetailsFragment()
                            detailsFragment.setDescription(
                                it.images.lg, it.name, it.biography.firstAppearance
                            )
                            parentFragmentManager.beginTransaction()
                                .add(R.id.list, detailsFragment)
                                .addToBackStack(detailsFragment.javaClass.name)
                                .commit()
                        }
                        list.adapter = myAdapter
                    }
                }
                is MyViewModel.UIState.Processing -> Unit
                is MyViewModel.UIState.Error -> {
                    Log.e("response error", it.description)
                }
            }
        }

        list.layoutManager = LinearLayoutManager(view.context)

    }
}