package com.rkeru.expensesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rkeru.expensesapp.data.model.ExpensesSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface SummaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(summary: ExpensesSummary)

    @Update
    suspend fun update(summary: ExpensesSummary)

    @Delete
    suspend fun delete(summary: ExpensesSummary)

    @Query("select * from summary where id = :id")
    fun getSummary(id: Int): Flow<ExpensesSummary>

    @Query("select * from summary " +
            "where month = :currentMonth and year = :currentYear")
    fun getSummaryOfMonth(
        currentMonth: Int,
        currentYear: Int
    ): Flow<List<ExpensesSummary>>

    @Query("select * from summary " +
            "where month = :currentMonth and year = :currentYear " +
            "and categoryId = :categoryId")
    fun getSummaryOfMonthOfCategory(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int
    )

    @Query("select * from summary " +
            "where month = :currentMonth and year = :currentYear " +
            "and sourceId = :sourceId")
    fun getSummaryOfMonthOfSource(
        currentMonth: Int,
        currentYear: Int,
        sourceId: Int
    )

    @Query("select * from summary " +
            "where month = :currentMonth and year = :currentYear " +
            "and categoryId = :categoryId and sourceId = :sourceId")
    fun getSummaryOfMonthOfCategoryAndSource(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int,
        sourceId: Int
    )

    @Query("SELECT * FROM summary " +
            "WHERE (year = :startYear AND month >= :startMonth) " +
            "OR (year = :endYear AND month <= :endMonth) " +
            "OR (year > :startYear AND year < :endYear)")
    fun getSummaryBetweenMonthAndYear(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int
    ): Flow<List<ExpensesSummary>>

    @Query("SELECT * FROM summary " +
            "WHERE (year = :startYear AND month >= :startMonth) " +
            "OR (year = :endYear AND month <= :endMonth) " +
            "OR (year > :startYear AND year < :endYear) " +
            "AND categoryId = :categoryId")
    fun getSummaryBetweenMonthAndYearOfCategory(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int
    ): Flow<List<ExpensesSummary>>

    @Query("SELECT * FROM summary " +
            "WHERE (year = :startYear AND month >= :startMonth) " +
            "OR (year = :endYear AND month <= :endMonth) " +
            "OR (year > :startYear AND year < :endYear) " +
            "AND sourceId = :sourceId")
    fun getSummaryBetweenMonthAndYearOfSource(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>


    @Query("SELECT * FROM summary " +
            "WHERE (year = :startYear AND month >= :startMonth) " +
            "OR (year = :endYear AND month <= :endMonth) " +
            "OR (year > :startYear AND year < :endYear) " +
            "AND categoryId = :categoryId AND sourceId = :sourceId")
    fun getSummaryBetweenMonthAndYearOfCategoryAndSource(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>
}