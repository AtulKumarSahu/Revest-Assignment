package com.revest.assignment

import android.app.Application
import com.revest.assignment.di.initKoin
import org.koin.android.ext.koin.androidContext

class RevestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RevestApp)
        }
    }
}
