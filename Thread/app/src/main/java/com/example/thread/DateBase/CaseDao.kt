package com.example.thread.DateBase

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
}