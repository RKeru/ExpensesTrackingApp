package com.rkeru.expensesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rkeru.expensesapp.data.model.Transaction
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

    @Query("select * from `transaction`")
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

    @Query("select * from `transaction` " +
            "where categoryId = :categoryId and sourceId = :sourceId " +
            "and date between :startDate and :endDate")
    fun getAllTransactionsFromCategoryAndSourceBetweenDates(
        categoryId: Int,
        sourceId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<Transaction>>

}