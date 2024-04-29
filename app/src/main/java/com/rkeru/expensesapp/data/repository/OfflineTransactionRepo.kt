package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.dao.TransactionDao
import com.rkeru.expensesapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineTransactionRepo (private val transactionDao: TransactionDao) : TransactionRepo {
    override fun getTransactionStream(id: Int): Flow<Transaction?> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromCategoryStream(categoryId: Int): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromSourceStream(sourceId: Int): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromCategoryAndSourceStream(
        categoryId: Int,
        sourceId: Int
    ): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getAllTransactionsStream(): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsBetweenDatesStream(
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromCategoryBetweenDatesStream(
        categoryId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromSourceBetweenDatesStream(
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionsFromCategoryAndSourceBetweenDatesStream(
        categoryId: Int,
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}