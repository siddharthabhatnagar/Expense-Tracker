package com.project.expensetracker.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 1)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract val expenseDao:ExpenseDao
}