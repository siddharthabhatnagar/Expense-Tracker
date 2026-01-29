package com.project.expensetracker.repository

import com.project.expensetracker.room.Expense
import com.project.expensetracker.room.ExpenseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    fun getAllExpenses(): Flow<List<Expense>> =
        expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) =
        expenseDao.insertExpense(expense)

    suspend fun deleteExpense(expense: Expense) =
        expenseDao.deleteExpense(expense)

    suspend fun getMonthlyTotal(
        startDate: Long,
        endDate: Long
    ): Double {
        return expenseDao.getTotalExpense(startDate, endDate) ?: 0.0
    }
}
