package com.ptithcm.applambaikiemtra.ui.debai.debaionline


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.repository.Repository

class DownDeBaiViewModel(val repo:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var listFirebase:MutableLiveData<MutableList<String>> = MutableLiveData()
    var test: MutableLiveData<String> = MutableLiveData()
    var x:Int=-1
    fun loadData(bomon: String) {
        repo.getDataDeThiFromSQL(bomon){
            list.postValue(it)
        }
    }
    fun loadDataFromFireBase(bomon: String){
        repo.getDataFromFireStore(bomon){
            listFirebase.postValue(it)
        }
    }
    fun loadDataDeThitoSQl(bomon: String)
    {
        repo.getDataDeThiFromApiToSQL(bomon){
            list.postValue(it)
        }
    }
    fun updateDethi(deThi: DeThi)
    {
        repo.updateDeThi(deThi)
    }
}