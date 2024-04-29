package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.dao.CategoryDao
import com.rkeru.expensesapp.data.model.Category
import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepo (private val categoryDao: CategoryDao) : CategoryRepo {
    override fun getCategoryStream(id: Int): Flow<Category?> = categoryDao.getCategory(id)

    override fun getAllCategoryStream(): Flow<List<Category>> = categoryDao.getAllCategories()

    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)

    override suspend fun updateCategory(category: Category) = categoryDao.update(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

}