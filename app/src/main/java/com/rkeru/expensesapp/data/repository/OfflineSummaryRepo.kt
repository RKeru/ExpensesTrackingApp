package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.dao.SummaryDao
import com.rkeru.expensesapp.data.model.ExpensesSummary
import kotlinx.coroutines.flow.Flow

class OfflineSummaryRepo (private val summaryDao: SummaryDao) : SummaryRepo {
    override fun getSummaryStream(id: Int): Flow<ExpensesSummary?> {
        TODO("Not yet implemented")
    }

    override fun getSummaryOfMonthStream(
        currentMonth: Int,
        currentYear: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryOfMonthOfCategoryStream(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryOfMonthOfSourceStream(
        currentMonth: Int,
        currentYear: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryOfMonthOfCategoryAndSourceStream(
        currentMonth: Int,
        currentYear: Int,
        categoryId: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryBetweenMonthAndYearStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryBetweenMonthAndYearOfCategoryStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryBetweenMonthAndYearOfSourceStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override fun getSummaryBetweenMonthAndYearOfCategoryAndSourceStream(
        startMonth: Int,
        endMonth: Int,
        startYear: Int,
        endYear: Int,
        categoryId: Int,
        sourceId: Int
    ): Flow<List<ExpensesSummary>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSummary(summary: ExpensesSummary) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSummary(summary: ExpensesSummary) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSummary(summary: ExpensesSummary) {
        TODO("Not yet implemented")
    }
}