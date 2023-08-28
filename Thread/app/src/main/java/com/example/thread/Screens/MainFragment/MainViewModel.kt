package com.example.thread.Screens.MainFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thread.Consts
import com.example.thread.DateBase.CaseDB
import com.example.thread.Model.Case
import com.example.thread.Repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    private val caseDB: CaseDB
) :
    ViewModel() {

    val date: MutableLiveData<MutableList<Case>> = MutableLiveData()
    fun getCasesFromApi() {
        viewModelScope.launch {
            val cases = mutableListOf<Case>()
            for (caseName in Consts.cases) {
                val caseFromApi = repo.getCase(caseName)
                val existingCase = caseDB.caseDao().getCaseByName(caseName)

                if (existingCase != null) {
                    existingCase.lowest_price = caseFromApi.lowest_price
                    existingCase.median_price = caseFromApi.median_price
                    existingCase.volume = caseFromApi.volume
                    caseDB.caseDao().updateCase(existingCase)
                } else {
                    caseDB.caseDao().insertCase(caseFromApi.copy(name = caseName))
                }
                cases.add(caseFromApi)
            }
            date.postValue(cases)
        }
    }


}