package com.rkeru.expensesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rkeru.expensesapp.data.model.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(source: Source)

    @Update
    suspend fun update(source: Source)

    @Delete
    suspend fun delete(source: Source)

    @Query("select * from source where id = :id")
    fun getSource(id: Int): Flow<Source>

    @Query("select * from source")
    fun getAllSources(): Flow<List<Source>>

}