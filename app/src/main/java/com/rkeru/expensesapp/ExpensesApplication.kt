package com.rkeru.expensesapp

import android.app.Application
import com.rkeru.expensesapp.data.AppContainer
import com.rkeru.expensesapp.data.AppDataContainer

class ExpensesApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}