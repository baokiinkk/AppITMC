package com.example.applambaikiemtra.ui.cauhoi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.DeThi
import com.example.applambaikiemtra.data.repository.Repository
class ViewModel_BaiThi(val rep:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<BaiThi> > = MutableLiveData()
    var check:MutableLiveData<Boolean> = MutableLiveData(false)
    var text:MutableLiveData<String> = MutableLiveData("Thời Gian")
    var title:String=""
    fun listener()
    {
        check.postValue(true)
    }
    fun getData(id:Int)
    {
        rep.loadDataBaiThi(id){
            list.postValue(it)
        }
    }
    fun updateDeThi(deThi:DeThi)
    {
        rep.updateDeThi(deThi)
    }
    fun loadBaiThiToSQL(boMon:String,deThi:String,idDeThi:Int)
    {
        rep.loadDataBaiThiToSQL(boMon,deThi,idDeThi)
        {
            list.postValue(it)
        }
    }
}

