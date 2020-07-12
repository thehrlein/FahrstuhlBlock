package com.tobiapplications.fahrstuhlblock

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tobiapplications.fahrstuhlblock.koin.Koin
import com.tobiapplications.fahrstuhlblock.koin.KoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
        initFirebase()
    }

    private fun initKoin() {
        startKoin {
            logger(KoinLogger())
            androidContext(this@App)
            modules(Koin.getModules())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initFirebase() {
        Firebase.initialize(this)
    }
}