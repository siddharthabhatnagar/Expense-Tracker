package com.project.expensetracker.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("""
        SELECT SUM(amount) FROM expense
        WHERE date BETWEEN :startDate AND :endDate
    """)
    suspend fun getTotalExpense(startDate: Long, endDate: Long): Double?
}
