package com.project.expensetracker.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, String, Double) -> Unit
) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedCategory by remember { mutableStateOf("Food") }
    var amount by remember { mutableStateOf("") }

    val categories = listOf("Food", "Travel", "Shopping", "Bills", "Other")
    var categoryExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Expense") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // 📅 Manual Date Input
                DateInputRow { millis ->
                    selectedDate = millis
                }

                // 🏷️ Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedCategory = it
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                // 💰 Amount Input
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                enabled = selectedDate != null && amount.toDoubleOrNull() != null,
                onClick = {
                    onConfirm(
                        selectedDate!!,
                        selectedCategory,
                        amount.toDouble()
                    )
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DateInputRow(
    onDateChanged: (Long?) -> Unit
) {
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
            value = day,
            onValueChange = { if (it.length <= 2) day = it },
            label = { Text("DD") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = month,
            onValueChange = { if (it.length <= 2) month = it },
            label = { Text("MM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = year,
            onValueChange = { if (it.length <= 4) year = it },
            label = { Text("YYYY") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }

    LaunchedEffect(day, month, year) {
        onDateChanged(convertToMillis(day, month, year))
    }
}

fun convertToMillis(day: String, month: String, year: String): Long? {
    return try {
        if (day.isBlank() || month.isBlank() || year.isBlank()) return null

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        formatter.isLenient = false   // VERY IMPORTANT (prevents invalid dates)

        val date = formatter.parse("$day-$month-$year")
        date?.time
    } catch (e: Exception) {
        null
    }
}

