package com.rkeru.expensesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rkeru.expensesapp.data.dao.CategoryDao
import com.rkeru.expensesapp.data.dao.SourceDao
import com.rkeru.expensesapp.data.dao.SummaryDao
import com.rkeru.expensesapp.data.dao.TransactionDao
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.model.ExpensesSummary
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.data.model.Transaction

@Database(
    entities = [
        Source::class,
        Category::class,
        Transaction::class,
        ExpensesSummary::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ExpensesDatabase : RoomDatabase() {

    abstract fun sourceDao(): SourceDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun summaryDao(): SummaryDao

    companion object {
        @Volatile
        private var Instance: ExpensesDatabase? = null

        fun getDatabase(context: Context): ExpensesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ExpensesDatabase::class.java,
                    "expenses_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

            }
        }
    }

}