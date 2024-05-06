package com.rkeru.expensesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rkeru.expensesapp.data.model.Transaction
import com.rkeru.expensesapp.data.model.TransactionDetails
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("select * from `transaction` where id = :id")
    fun getTransaction(id: Int): Flow<Transaction>

    @Query("select * from `transaction` where categoryId = :categoryId")
    fun getAllTransactionsFromCategory(categoryId: Int): Flow<List<Transaction>>

    @Query("select * from `transaction` where sourceId = :sourceId")
    fun getAllTransactionsFromSource(sourceId: Int): Flow<List<Transaction>>

    @Query("select * from `transaction` " +
            "where categoryId = :categoryId and sourceId = :sourceId")
    fun getAllTransactionsFromCategoryAndSource(
        categoryId: Int,
        sourceId: Int
    ): Flow<List<Transaction>>

    @Query("select * from `transaction` order by date asc")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("select * from `transaction` " +
            "where date between :startDate and :endDate")
    fun getAllTransactionsBetweenDates(
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    @Query("select * from `transaction` where categoryId = :categoryId" +
            " and date between :startDate and :endDate")
    fun getAllTransactionsFromCategoryBetweenDates(
        categoryId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    @Query("select * from `transaction` where sourceId = :sourceId" +
            " and date between :startDate and :endDate")
    fun getAllTransactionsFromSourceBetweenDates(
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    @Query("select sum(quantity) from `transaction` where isExpense = 1 and " +
            "date between :startDate and :endDate")
    fun getAllIncomeBetweenDates(
        startDate: Date,
        endDate: Date
    ): Flow<Double>

    @Query("select sum(quantity) from `transaction` where isExpense = 0 and " +
            "date between :startDate and :endDate")
    fun getAllExpensesBetweenDates(
        startDate: Date,
        endDate: Date
    ): Flow<Double>

    @Query("select * from `transaction` " +
            "where categoryId = :categoryId and sourceId = :sourceId " +
            "and date between :startDate and :endDate")
    fun getAllTransactionsFromCategoryAndSourceBetweenDates(
        categoryId: Int,
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

    /**
     * Transaction Details with category and source naming
     */

    @Query("select `transaction`.id as transactionId, " +
            "`transaction`.title as title, " +
            "`transaction`.isExpense as isExpense, " +
            "`transaction`.quantity as quantity, " +
            "`transaction`.note as note," +
            "source.id as sourceId, " +
            "source.name as sourceName, " +
            "category.id as categoryId, " +
            "category.name as categoryName, " +
            "`transaction`.date as date" +
            " from `transaction`, category, source " +
            "where category.id = `transaction`.categoryId and " +
            "source.id = `transaction`.sourceId " +
            "order by date desc")
    fun getAllTransactionsWithDetails(): Flow<List<TransactionDetails>>

    @Query("select `transaction`.id as transactionId, " +
            "`transaction`.title as title, " +
            "`transaction`.isExpense as isExpense, " +
            "`transaction`.quantity as quantity, " +
            "`transaction`.note as note," +
            "source.id as sourceId, " +
            "source.name as sourceName, " +
            "category.id as categoryId, " +
            "category.name as categoryName, " +
            "`transaction`.date as date" +
            " from `transaction`, category, source " +
            "where category.id = `transaction`.categoryId and " +
            "source.id = `transaction`.sourceId and " +
            "`transaction`.id = :id ")
    fun getTransactionWithDetails(id: Int): Flow<TransactionDetails>

}