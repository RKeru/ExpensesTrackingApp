package com.rkeru.expensesapp.data.model

import java.util.Date

data class TransactionDetails(
    val transactionId: Int,
    val title: String,
    val isExpense: Boolean,
    val quantity: Double,
    val note: String,
    val sourceId: Int,
    val sourceName: String,
    val categoryId: Int,
    val categoryName: String,
    val date: Date
)
