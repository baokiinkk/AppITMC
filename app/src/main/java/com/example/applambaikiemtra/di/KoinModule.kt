package com.example.applambaikiemtra.di

import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.db.AppDatabase
import com.example.applambaikiemtra.data.repository.Repository
import com.example.applambaikiemtra.ui.boMon.ViewModel_BoMon
import com.example.applambaikiemtra.ui.cauhoi.ViewModel_BaiThi
import com.example.applambaikiemtra.ui.debai.ViewModel_DeBai
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class KoinModule {
}
val deBaiVMModule= module {
    viewModel{ViewModel_DeBai(get())}
}
val cauHoiVMModule= module {
    viewModel { ViewModel_BaiThi(get()) }
}
val firebaseModule= module {
    single { firestore() }
}
val appdaoModule= module {
    single<AppDao> { AppDatabase.getInstance(androidApplication()).appDao()}
}
val bomonVMModel= module {
    viewModel { ViewModel_BoMon(get()) }
}
val repoModule = module {
    single { Repository(get(),get()) }
}