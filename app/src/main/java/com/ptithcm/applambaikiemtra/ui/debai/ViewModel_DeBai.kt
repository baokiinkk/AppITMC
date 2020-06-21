package com.ptithcm.applambaikiemtra.ui.debai


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.repository.Repository

class ViewModel_DeBai(val repo:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<DeThi>> = MutableLiveData()
    var listCauHoi:MutableLiveData<MutableList<BaiThi>> = MutableLiveData()
    var test: MutableLiveData<String> = MutableLiveData()
    var score: MutableLiveData<Int> = MutableLiveData()


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
    fun updateDeThi(deThi: DeThi){
        repo.updateDeThi(deThi)
    }
    fun loadBaiThiToSQL(boMon:String,deThi:String)
    {
        repo.getDataCauHoiFromApiToSQL(boMon,deThi)
        {
            listCauHoi.postValue(it)
        }
    }
    fun updateSocre(int:Int){
        repo.updateScore(int)
    }
    fun getScore(){
        repo.getDataScore {
            score.postValue(it)
        }
    }
}