package com.example.thread.Screens.MainFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thread.Consts
import com.example.thread.Model.Case
import com.example.thread.Repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    val date: MutableLiveData<MutableList<Case>> = MutableLiveData()
    fun getCases() {
        viewModelScope.launch {
            val cases = mutableListOf<Case>()
            for (i in Consts.cases) {
                val case = repo.getCase(i)
                cases.add(case)
                Log.d("!!! cases", "getCases:$case ")
            }
            date.postValue(cases)
            Log.d("!!! date", "getCases: ${date.value}}")
        }
    }

}