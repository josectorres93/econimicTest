package com.visma.economic

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }
    companion object {
        lateinit var application: Application
    }
}