package com.example.thread.DateBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thread.Model.Case

@Dao
interface CaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCase(case: Case)

    @Update
    suspend fun updateCase(case: Case)

    @Query("SELECT * FROM Cases WHERE name = :name")
    suspend fun getCaseByName(name: String): Case?
    @Update
    suspend fun updateCases(cases: List<Case>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCases(cases: List<Case>)

    @Query("SELECT * FROM Cases")
    fun getAllCases(): LiveData<List<Case>>

    @Query("SELECT * FROM Cases")
    fun getAllCasesList(): List<Case>

    @Query("SELECT COUNT(*) FROM Cases")
    suspend fun getRowCount(): Int
}