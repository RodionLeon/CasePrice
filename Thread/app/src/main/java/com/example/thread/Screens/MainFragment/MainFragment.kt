package com.example.thread.Screens.MainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.API.ApiService
import com.example.thread.Consts
import com.example.thread.Model.Case

import com.example.thread.R
import com.example.thread.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var adapter: MainAdapter
    private lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        rv = view.findViewById(R.id.rv)

        adapter = MainAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        adapter.onItemLongClickListener = { position ->
            val case = adapter.getCaseAtPosition(position)
            if (case.isFavorite) {
                viewModel.markAsNotFavorite(case.name)
            } else {
                viewModel.markAsFavorite(case.name)
            }
        }


        viewModel.date.observe(viewLifecycleOwner) { cases ->
            adapter.setCases(cases)
        }

        viewModel.getAllCasesListFromDb().observe(viewLifecycleOwner) { cases ->
            adapter.setCases(cases)
        }

        view.findViewById<Button>(R.id.getCase).setOnClickListener {
            viewModel.getCasesFromApi()
        }


        return view
    }
}





