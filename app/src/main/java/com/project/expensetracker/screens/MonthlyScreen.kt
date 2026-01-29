package com.project.expensetracker.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.expensetracker.room.Expense
import com.project.expensetracker.screens.components.ExpenseItem
import com.project.expensetracker.viewmodel.ExpenseViewModel

@Composable
fun MonthyScreen(
    viewModel: ExpenseViewModel
) {
    val years by viewModel.availableYears.collectAsState(initial = emptyList())
    val months by viewModel.availableMonths.collectAsState(initial = emptyList())
    val expenses by viewModel.filteredExpenses.collectAsState(initial = emptyList())
    val selectedYear by viewModel.selectedYear.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    var total=expenses.sumOf { it.amount.toInt() }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Analytics",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        YearSelector(
            years = years,
            selectedYear = selectedYear,
            onYearSelected = viewModel::selectYear
        )

        if (selectedYear != null) {
            MonthSelector(
                months = months,
                selectedMonth = selectedMonth,
                onMonthSelected = viewModel::selectMonth
            )
        }

        ExpensePieChart(expenses = expenses)
        Text(text="Total->${total.toString()}", modifier = Modifier.padding(20.dp))
        LazyColumn {
            items(expenses){
                ExpenseItem(it,{viewModel.deleteExpense(it)})
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun YearSelector(
    years: List<Int>,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(years) { year ->
            FilterChip(
                selected = year == selectedYear,
                onClick = { onYearSelected(year) },
                label = { Text(year.toString()) }
            )
        }
    }
}
@Composable
fun MonthSelector(
    months: List<Int>,
    selectedMonth: Int?,
    onMonthSelected: (Int) -> Unit
) {
    val monthNames = listOf(
        "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(months) { month ->
            FilterChip(
                selected = month == selectedMonth,
                onClick = { onMonthSelected(month) },
                label = { Text(monthNames[month]) }
            )
        }
    }
}
@Composable
fun ExpensePieChart(
    expenses: List<Expense>,
    modifier: Modifier = Modifier
) {
    val grouped = expenses.groupBy { it.category }
        .mapValues { it.value.sumOf { e -> e.amount.toDouble() } }

    val total = grouped.values.sum()
    if (total <= 0) return

    val categoryPercentages = grouped.mapValues {
        (it.value / total * 100)
    }

    val colors = listOf(
        Color(0xFFEF5350),
        Color(0xFFAB47BC),
        Color(0xFF42A5F5),
        Color(0xFF26A69A),
        Color(0xFFFFCA28)
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🥧 Pie Chart
        Canvas(
            modifier = Modifier
                .size(240.dp)
        ) {
            var startAngle = -90f

            categoryPercentages.entries.forEachIndexed { index, entry ->
                val sweepAngle = (entry.value * 3.6f).toFloat()

                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📊 Legend with Percentages
        categoryPercentages.entries.forEachIndexed { index, entry ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = colors[index % colors.size],
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${entry.key}: ${"%.1f".format(entry.value)}%",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
