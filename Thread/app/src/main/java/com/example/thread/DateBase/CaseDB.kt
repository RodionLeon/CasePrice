package com.example.thread.DateBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thread.Model.Case

@Database(entities = [Case::class], version = 1)
abstract class CaseDB: RoomDatabase() {
    abstract fun caseDao(): CaseDao
}