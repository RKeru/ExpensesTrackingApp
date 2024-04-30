package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.dao.TransactionDao
import com.rkeru.expensesapp.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineTransactionRepo (private val transactionDao: TransactionDao) : TransactionRepo {
    override fun getTransactionStream(
        id: Int
    ): Flow<Transaction?> = transactionDao.getTransaction(id)

    override fun getTransactionsFromCategoryStream(
        categoryId: Int
    ): Flow<List<Transaction>> = transactionDao.getAllTransactionsFromCategory(categoryId)

    override fun getTransactionsFromSourceStream(
        sourceId: Int
    ): Flow<List<Transaction>> = transactionDao.getAllTransactionsFromSource(sourceId)

    override fun getTransactionsFromCategoryAndSourceStream(
        categoryId: Int,
        sourceId: Int
    ): Flow<List<Transaction>> =
        transactionDao.getAllTransactionsFromCategoryAndSource(categoryId, sourceId)

    override fun getAllTransactionsStream(): Flow<List<Transaction>> =
        transactionDao.getAllTransactions()
    override fun getTransactionsBetweenDatesStream(
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> = transactionDao.getAllTransactionsBetweenDates(startDate, endDate)

    override fun getTransactionsFromCategoryBetweenDatesStream(
        categoryId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> =
        transactionDao.getAllTransactionsFromCategoryBetweenDates(categoryId, startDate, endDate)

    override fun getTransactionsFromSourceBetweenDatesStream(
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> =
        transactionDao.getAllTransactionsFromSourceBetweenDates(sourceId, startDate, endDate)

    override fun getTransactionsFromCategoryAndSourceBetweenDatesStream(
        categoryId: Int,
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>> =
        transactionDao.getAllTransactionsFromCategoryAndSourceBetweenDates(
            categoryId, sourceId, startDate, endDate
        )

    override suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)

    override suspend fun updateTransaction(transaction: Transaction) = transactionDao.update(transaction)

    override suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
}