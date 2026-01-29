package com.project.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.expensetracker.repository.ExpenseRepository
import com.project.expensetracker.room.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    val expenses: StateFlow<List<Expense>> =
        repository.getAllExpenses()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun addExpense(
        date: Long,
        category: String,
        amount: Double
    ) {
        viewModelScope.launch {
            repository.insertExpense(
                Expense(
                    date = date,
                    category = category,
                    amount = amount
                )
            )
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    suspend fun getMonthlyTotal(
        startDate: Long,
        endDate: Long
    ): Double {
        return repository.getMonthlyTotal(startDate, endDate)
    }

    private val _selectedYear = MutableStateFlow<Int?>(null)
    val selectedYear = _selectedYear.asStateFlow()

    private val _selectedMonth = MutableStateFlow<Int?>(null)
    val selectedMonth = _selectedMonth.asStateFlow()

    fun selectYear(year: Int) {
        _selectedYear.value = year
        _selectedMonth.value = null
    }

    fun selectMonth(month: Int) {
        _selectedMonth.value = month
    }

    val availableYears = expenses.map { list ->
        list.map {
            Calendar.getInstance().apply { timeInMillis = it.date }
                .get(Calendar.YEAR)
        }.distinct().sortedDescending()
    }

    val availableMonths = combine(expenses, selectedYear) { list, year ->
        if (year == null) emptyList()
        else {
            list.map {
                Calendar.getInstance().apply { timeInMillis = it.date }
            }.filter {
                it.get(Calendar.YEAR) == year
            }.map {
                it.get(Calendar.MONTH)
            }.distinct().sorted()
        }
    }

    val filteredExpenses = combine(
        expenses,
        selectedYear,
        selectedMonth
    ) { list, year, month ->
        list.filter {
            val cal = Calendar.getInstance().apply { timeInMillis = it.date }
            (year == null || cal.get(Calendar.YEAR) == year) &&
                    (month == null || cal.get(Calendar.MONTH) == month)
        }
    }
}
