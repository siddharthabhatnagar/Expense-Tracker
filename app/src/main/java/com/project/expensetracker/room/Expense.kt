package com.project.expensetracker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val date:Long,
    val category:String,
    val amount:Double
)
