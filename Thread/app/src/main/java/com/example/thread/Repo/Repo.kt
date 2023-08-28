package com.example.thread.Repo

import com.example.thread.API.ApiService
import com.example.thread.Model.Case
import javax.inject.Inject

class Repo @Inject constructor(
    private val provideApiService: ApiService
) {
    suspend fun getCase(caseName:String): Case{
        return provideApiService.getCase(caseName)
    }
}
