package com.example.thread.Screens.FavoriteListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.thread.DateBase.CaseDB
import com.example.thread.Model.Case
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val caseDB: CaseDB
) : ViewModel() {

    val favoriteCases: LiveData<List<Case>> = caseDB.caseDao().getFavoriteCases()
    suspend fun removeFromFavorites(caseName: String) {
        caseDB.caseDao().updateFavoriteStatus(caseName, false)
    }
}