package com.example.thread.Screens.MainFragment

import android.util.Log
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    private val caseDB: CaseDB
) :
    ViewModel() {

    val date: MutableLiveData<MutableList<Case>> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val casesFromDb = caseDB.caseDao().getAllCasesList()
            date.postValue(casesFromDb.toMutableList())
        }
    }
    init {
        viewModelScope.launch {
            if (caseDB.isDatabaseEmpty()) {
                getCasesFromApi()
            }
        }
    }

    fun getCasesFromApi() {
        viewModelScope.launch {
            val casesToUpdate = mutableListOf<Case>()
            val casesToAdd = mutableListOf<Case>()
            var delayMillis = 1000L

            for ((index, caseName) in Consts.cases.withIndex()) {
                while (true) {
                    try {
                        val caseFromApi = repo.getCase(caseName)
                        Log.d("!!!", "getCasesFromApi: $caseFromApi ")
                        val existingCase = caseDB.caseDao().getCaseByName(caseName)

                        if (existingCase != null) {
                            existingCase.lowest_price = caseFromApi.lowest_price
                            existingCase.median_price = caseFromApi.median_price
                            existingCase.volume = caseFromApi.volume
                            casesToUpdate.add(existingCase)
                        } else {
                            casesToAdd.add(caseFromApi.copy(name = caseName))
                        }
                        break
                    } catch (e: HttpException) {
                        if (e.code() == 429) {
                            delay(delayMillis)
                            delayMillis *= 2
                        } else {
                            break
                        }
                    } catch (e: Exception) {
                        break
                    }
                }
                delayMillis = 1000L
            }

            caseDB.caseDao().updateCases(casesToUpdate)
            caseDB.caseDao().insertCases(casesToAdd)
        }
    }

    fun getAllCasesListFromDb(): LiveData<List<Case>> {
        return repo.getAllCasesFromDb()
    }

}