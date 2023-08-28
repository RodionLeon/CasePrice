package com.example.thread.Repo

import androidx.lifecycle.LiveData
import com.example.thread.API.ApiService
import com.example.thread.DateBase.CaseDao
import com.example.thread.Model.Case
import javax.inject.Inject

class Repo @Inject constructor(
    private val provideApiService: ApiService,
    private val caseDao: CaseDao
) {
    suspend fun getCase(caseName:String): Case{
        return provideApiService.getCase(caseName)
    }
    fun getAllCasesFromDb(): LiveData<List<Case>> {
        return caseDao.getAllCases()
    }
}
