package com.rkeru.expensesapp.data

import android.content.Context
import com.rkeru.expensesapp.data.repository.CategoryRepo
import com.rkeru.expensesapp.data.repository.OfflineCategoryRepo
import com.rkeru.expensesapp.data.repository.OfflineSourceRepo
import com.rkeru.expensesapp.data.repository.OfflineSummaryRepo
import com.rkeru.expensesapp.data.repository.OfflineTransactionRepo
import com.rkeru.expensesapp.data.repository.SourceRepo
import com.rkeru.expensesapp.data.repository.SummaryRepo
import com.rkeru.expensesapp.data.repository.TransactionRepo

interface AppContainer {
    val categoryRepo: CategoryRepo
    val sourceRepo: SourceRepo
    val summaryRepo: SummaryRepo
    val transactionRepo: TransactionRepo
}

class AppDataContainer(
    private val context: Context
) : AppContainer {

    override val categoryRepo: CategoryRepo by lazy {
        OfflineCategoryRepo(ExpensesDatabase.getDatabase(context).categoryDao())
    }

    override val sourceRepo: SourceRepo by lazy {
        OfflineSourceRepo(ExpensesDatabase.getDatabase(context).sourceDao())
    }

    override val summaryRepo: SummaryRepo by lazy {
        OfflineSummaryRepo(ExpensesDatabase.getDatabase(context).summaryDao())
    }

    override val transactionRepo: TransactionRepo by lazy {
        OfflineTransactionRepo(ExpensesDatabase.getDatabase(context).transactionDao())
    }
}