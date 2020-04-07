package com.example.applambaikiemtra.ui.debai


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.repository.Repository
import kotlinx.coroutines.launch

class ViewModel_DeBai(val repo:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var toLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
    var tocheck: MutableLiveData<Boolean?> = MutableLiveData(null)
    var test: MutableLiveData<String> = MutableLiveData()
    fun loadData(bomon: String) {
        repo.loadDataDeThi(bomon){
            list.postValue(it)
        }
    }
    fun loadDatatoSQl(bomon: String)
    {
        repo.loadDataDeThiToSQL(bomon)
        {
            list.postValue(it)
        }
    }
    fun loadDataBaiThiToSQL(bomon: String,deThi: String,int: Int)
    {
        repo.loadDataBaiThiToSQL(bomon,deThi,int)
    }
}