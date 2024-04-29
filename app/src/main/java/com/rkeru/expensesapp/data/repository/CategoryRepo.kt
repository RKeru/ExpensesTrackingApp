package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepo {

    fun getCategoryStream(id: Int): Flow<Category?>

    fun getAllCategoryStream(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)

}