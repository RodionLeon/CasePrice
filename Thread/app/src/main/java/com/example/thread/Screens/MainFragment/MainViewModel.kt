package com.example.thread.Screens.MainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thread.Consts
import com.example.thread.DateBase.CaseDB
import com.example.thread.Model.Case
import com.example.thread.Repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            for (caseName in Consts.cases) {
                while (true) {
                    try {
                        val caseFromApi = repo.getCase(caseName)
                        val existingCase = caseDB.caseDao().getCaseByName(caseName)

                        if (existingCase != null) {
                            val sellPriceDifference = parsePrice(caseFromApi.median_price) - parsePrice(existingCase.median_price)
                            val buyPriseDifference = parsePrice(caseFromApi.lowest_price) - parsePrice(existingCase.lowest_price)
                            val sellPriceComparison = sellPriceDifference > 0
                            val buyPriceComparison = buyPriseDifference > 0
                            existingCase.lowest_price = caseFromApi.lowest_price
                            existingCase.median_price = caseFromApi.median_price
                            existingCase.volume = caseFromApi.volume
                            existingCase.buyPriceDifference = buyPriseDifference
                            existingCase.sellPriceDifference = sellPriceDifference
                            existingCase.buyPriceComparison = buyPriceComparison
                            existingCase.sellPriceComparison = sellPriceComparison
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

            val updatedCases = date.value?.toMutableList() ?: mutableListOf()
            updatedCases.addAll(casesToUpdate)
            updatedCases.addAll(casesToAdd)
            date.postValue(updatedCases)
        }
    }

    fun getAllCasesListFromDb(): LiveData<List<Case>> {
        return repo.getAllCasesFromDb()
    }
    fun markAsFavorite(caseName: String) {
        viewModelScope.launch {
            caseDB.caseDao().updateFavoriteStatus(caseName, true)
        }
    }
    fun markAsNotFavorite(caseName: String) {
        viewModelScope.launch {
            caseDB.caseDao().updateFavoriteStatus(caseName, false)
        }
    }

    private fun parsePrice(priceString: String): Double {
        val priceWithoutDollarSign = priceString.removePrefix("$")
        return priceWithoutDollarSign.toDoubleOrNull() ?: 0.0
    }

}