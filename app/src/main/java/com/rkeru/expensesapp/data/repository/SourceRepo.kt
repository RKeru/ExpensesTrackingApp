package com.rkeru.expensesapp.data.repository

import com.rkeru.expensesapp.data.model.Source
import kotlinx.coroutines.flow.Flow

interface SourceRepo {

    fun getSourceStream(id: Int): Flow<Source?>

    fun getAllSourcesStream(): Flow<List<Source>>

    suspend fun insertSource(source: Source)

    suspend fun updateSource(source: Source)

    suspend fun deleteSource(source: Source)

}