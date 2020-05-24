package com.example.applambaikiemtra

import android.app.Application
import com.example.applambaikiemtra.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


// tiêm DI
class App :Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                firebaseModule,
                deBaiVMModule,
                cauHoiVMModule,
                bomonVMModel,
                appdaoModule,
                repoModule
            ))
        }
    }
}