package com.ptithcm.applambaikiemtra.di

import com.ptithcm.applambaikiemtra.data.api.firestore
import com.ptithcm.applambaikiemtra.data.db.AppDao
import com.ptithcm.applambaikiemtra.data.db.AppDatabase
import com.ptithcm.applambaikiemtra.data.repository.Repository
import com.ptithcm.applambaikiemtra.ui.boMon.ViewModel_BoMon
import com.ptithcm.applambaikiemtra.ui.cauhoi.ViewModel_CauHoi
import com.ptithcm.applambaikiemtra.ui.debai.ViewModel_DeBai
import com.ptithcm.applambaikiemtra.ui.downdebai.DownDeBaiViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// dùng DI để tiêm các lớp lại với nhau. mục đích là để mỗi class làm 1 công việc nhất định. ko được new 1 class nào trong 1 class
// tuy làm chậm chương trình nhưng sẽ dễ dàng thay đổi các class mà k làm ảnh hưởng đến những class khác.
// VD: muốn thay đổi không dùng api là firestore nữa. thì chỉ cần thay đổi class firestore mà ko cần phải sửa đổi ở những class khác
// đây là cái khó nhất trong mô hình code mvvm
class KoinModule {
}
val deBaiVMModule= module {
    viewModel{ViewModel_DeBai(get())}
}
val cauHoiVMModule= module {
    viewModel { ViewModel_CauHoi(get()) }
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

val downDebaiModule = module {
    viewModel {DownDeBaiViewModel(get())}
}