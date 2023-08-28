package com.example.thread.hilt.modules

import android.content.Context
import androidx.room.Room
import com.example.thread.DateBase.CaseDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCaseDB(@ApplicationContext context: Context): CaseDB {
        return Room.databaseBuilder(context, CaseDB::class.java, "case_database")
            .build()
    }
}