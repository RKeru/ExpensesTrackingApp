package com.rkeru.expensesapp

import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.model.Source

/**
 * Convert Int to booleans
 * 0 -> false
 * anything else -> true
 */
val Int.toBool: Boolean
    get() = this != 0

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}