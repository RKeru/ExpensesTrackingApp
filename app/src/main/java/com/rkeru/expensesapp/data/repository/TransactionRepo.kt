package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.model.Transaction
import com.rkeru.expensesapp.data.model.TransactionDetails
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TransactionRepo {

    fun getTransactionStream(id: Int): Flow<Transaction?>

    fun getTransactionsFromCategoryStream(categoryId: Int): Flow<List<Transaction>>

    fun getTransactionsFromSourceStream(sourceId: Int): Flow<List<Transaction>>

    fun getTransactionsFromCategoryAndSourceStream(
        categoryId: Int,
        sourceId: Int
    ): Flow<List<Transaction>>

    fun getAllTransactionsStream(): Flow<List<Transaction>>

    fun getTransactionsBetweenDatesStream(
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    fun getTransactionsFromCategoryBetweenDatesStream(
        categoryId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    fun getTransactionsFromSourceBetweenDatesStream(
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    fun getTransactionsFromCategoryAndSourceBetweenDatesStream(
        categoryId: Int,
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    fun getAllIncomesBetweenDatesStream(
        startDate: Date,
        endDate: Date
    ): Flow<Double>

    fun getAllExpensesBetweenDatesStream(
        startDate: Date,
        endDate: Date
    ): Flow<Double>

    fun getAllDetailedExpensesStream(): Flow<List<TransactionDetails>>

    fun getDetailedExpenseStream(id: Int): Flow<TransactionDetails>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

}