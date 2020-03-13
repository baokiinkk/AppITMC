package com.example.applambaikiemtra.di

import com.example.applambaikiemtra.network.firestore
import com.example.applambaikiemtra.ui.boMon.ViewModel_BoMon
import com.example.applambaikiemtra.ui.cauhoi.AdapterRecycleView
import com.example.applambaikiemtra.ui.cauhoi.ViewModel_BaiThi
import com.example.applambaikiemtra.ui.debai.ViewModel_DeBai
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
val bomonVMModel= module {
    viewModel { ViewModel_BoMon(get()) }
}