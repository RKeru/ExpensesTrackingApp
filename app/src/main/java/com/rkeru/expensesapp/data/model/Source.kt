package com.rkeru.expensesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class Source(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val initialBalance: Double
)
