package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.model.ExpensesSummary
import kotlinx.coroutines.flow.Flow

interface SummaryRepo {

    fun getSummaryStream(id: Int): Flow<ExpensesSummary?>

    fun getSummaryOfMonthStream(
        currentMonth: Int,
        currentYear: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryOfMonthOfCategoryStream(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryOfMonthOfSourceStream(
        currentMonth: Int,
        currentYear: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryOfMonthOfCategoryAndSourceStream(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryBetweenMonthAndYearStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryBetweenMonthAndYearOfCategoryStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryBetweenMonthAndYearOfSourceStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>

    fun getSummaryBetweenMonthAndYearOfCategoryAndSourceStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>>

    suspend fun insertSummary(summary: ExpensesSummary)

    suspend fun updateSummary(summary: ExpensesSummary)

    suspend fun deleteSummary(summary: ExpensesSummary)

}