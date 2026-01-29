package com.project.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.project.expensetracker.room.ExpenseDao
import com.project.expensetracker.room.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ContactProvider {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context):ExpenseDatabase{
        return Room.databaseBuilder(context,ExpenseDatabase::class.java,"expense_db").build()

    }
    @Provides
    @Singleton
    fun daoProvider(db:ExpenseDatabase):ExpenseDao{
        return db.expenseDao

    }
}