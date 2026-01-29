package com.project.expensetracker.screens

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.expensetracker.navigation.Monthly
import com.project.expensetracker.screens.components.AddExpenseDialog
import com.project.expensetracker.screens.components.ExpenseItem
import com.project.expensetracker.viewmodel.ExpenseViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(navHostController: NavHostController,modifier: Modifier = Modifier) {
    var addExpenseDialog by remember { mutableStateOf(false) }
    val viewmodel= hiltViewModel<ExpenseViewModel>()
    val expenses by viewmodel.expenses.collectAsState()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
            Button(onClick = { navHostController.navigate(Monthly) } ) {
                Text(text = "See Monthly Reports")
            }
            LazyColumn(
                modifier = modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                    items(items = expenses){expense->
                        ExpenseItem(expense = expense, onDelete = {viewmodel.deleteExpense(expense)})
                    }
            }
            Button(onClick = {addExpenseDialog=true}) {
                Text(text="Add Expense")
            }
        }
    if(addExpenseDialog){
        AddExpenseDialog(onDismiss = {addExpenseDialog=false}, onConfirm = {date,cat,amount-> viewmodel.addExpense(date,cat,amount)
                                        addExpenseDialog=false})
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMS(){
    MainScreen(rememberNavController())
}

