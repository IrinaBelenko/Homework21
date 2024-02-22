package com.example.homework21

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListFragment : Fragment() {

    private var onItemClick: (String) -> Unit = {}

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
        val api = ApiClient.client.create(ApiInterface::class.java)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        var text:String

        viewModel.getData()
        viewModel.uiState.observe(viewLifecycleOwner){
            when (it) {
                is MyViewModel.UIState.Empty -> Unit
                is MyViewModel.UIState.Result -> {
                    val detailsFragment = DetailsFragment()
                   // detailsFragment.setDescription(it.imageURL, it.nameHero, it.listValue)
                }

                is MyViewModel.UIState.Processing -> {
                    text = "Processing"
                }

                is MyViewModel.UIState.Error -> {
                    text = it.description
                }
        }}
//        api.getHeroes()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.isNotEmpty()) {
//                    val myAdapter = RecyclerViewAdapter(it as MutableList<Hero>) {
//                        val detailsFragment = DetailsFragment()
//                        detailsFragment.setDescription(it.images.lg, it.name, it.biography.firstAppearance)
//                        parentFragmentManager.beginTransaction()
//                            .add(R.id.list,detailsFragment)
//                            .addToBackStack(detailsFragment.javaClass.name)
//                            .commit()
//                    }
//                    list.adapter = myAdapter
//                }
//            }, {
//                Toast.makeText(view.context, "Fetch error ${it.message}", Toast.LENGTH_SHORT).show()
//            })
        list.layoutManager = LinearLayoutManager(view.context)

        fun setItemClickListener(lambda: (String) -> Unit) {
            onItemClick = lambda
        }
    }
}