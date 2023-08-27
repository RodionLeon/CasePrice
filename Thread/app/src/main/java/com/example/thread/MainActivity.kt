package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.API.ApiService
import com.example.thread.MainFragment.MainAdapter
import com.example.thread.Model.Case
import com.example.thread.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var rv: RecyclerView
    private val scope = CoroutineScope(Dispatchers.IO)
    private val apiService = ApiService.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        rv = binding.rv


        binding.getPost.setOnClickListener {
            scope.launch {
                val date = mutableListOf<Case>()
                for (i in Consts.cases) {
                    val case = apiService.getCase(i)
                    Log.d("!!!! case", "onCreate:$case ${i} ")
                    date.add(case)

                }
                withContext(Dispatchers.Main) {
                    rv.adapter = MainAdapter(date , this@MainActivity)
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}