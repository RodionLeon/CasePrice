package com.example.thread.Screens.FavoriteListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    @Inject
    lateinit var adapter: FavoriteListAdapter
    private lateinit var rv: RecyclerView

    private val viewModel: FavoriteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)
        rv = view.findViewById(R.id.favoriteRV)

        adapter = FavoriteListAdapter()

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.favoriteCases.observe(viewLifecycleOwner) { favoriteCases ->
            adapter.favoriteCaseList = favoriteCases.toMutableList()
            adapter.notifyDataSetChanged()
        }

        adapter.removeFavoriteItemClickListener = { position ->
            val favoriteCase = adapter.favoriteCaseList[position]
            viewModel.viewModelScope.launch { viewModel.removeFromFavorites(favoriteCase.name) }
        }

        return view
    }
}