package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.dao.SourceDao
import com.rkeru.expensesapp.data.model.Source
import kotlinx.coroutines.flow.Flow

class OfflineSourceRepo (private val sourceDao: SourceDao) : SourceRepo {
    override fun getSourceStream(id: Int): Flow<Source?> = sourceDao.getSource(id)

    override fun getAllSourcesStream(): Flow<List<Source>> = sourceDao.getAllSources()

    override suspend fun insertSource(source: Source) = sourceDao.insert(source)

    override suspend fun updateSource(source: Source) = sourceDao.update(source)

    override suspend fun deleteSource(source: Source) = sourceDao.delete(source)
}