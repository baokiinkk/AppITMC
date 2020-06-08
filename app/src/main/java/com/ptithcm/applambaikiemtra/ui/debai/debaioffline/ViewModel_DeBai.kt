package com.ptithcm.applambaikiemtra.ui.debai.debaioffline


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.repository.Repository

class ViewModel_DeBai(val repo:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var listFirebase:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var test: MutableLiveData<String> = MutableLiveData()
    var x:Int=-1
    fun loadData(bomon: String) {
        repo.getDataDeThiFromSQL(bomon){
            list.postValue(it)
        }
    }
    fun loadDataDeThitoSQl(bomon: String)
    {
        repo.getDataDeThiFromApiToSQL(bomon){
            list.postValue(it)
        }

    }
}