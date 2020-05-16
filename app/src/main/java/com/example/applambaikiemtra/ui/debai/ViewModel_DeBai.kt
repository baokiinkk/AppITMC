package com.example.applambaikiemtra.ui.debai


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.data.repository.Repository
import kotlinx.coroutines.launch

class ViewModel_DeBai(val repo:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var test: MutableLiveData<String> = MutableLiveData()
    var x:Int=-1
    fun loadData(bomon: String) {
        repo.loadDataDeThi(bomon){
            list.postValue(it)
        }
    }
}