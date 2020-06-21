package com.ptithcm.applambaikiemtra.ui.cauhoi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.repository.Repository
class ViewModel_CauHoi(val rep:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<BaiThi> > = MutableLiveData()
    var check:MutableLiveData<Boolean> = MutableLiveData(false)
    var text:MutableLiveData<String> = MutableLiveData("Thời Gian")
    var scor:MutableLiveData<Int> = MutableLiveData()

    var title:String=""
    fun listener()
    {
        check.postValue(true)
    }
    fun getData(id:String)
    {
        rep.getDataCauHoiFromSQL(id){
            list.postValue(it)
        }

    }
    fun updateDeThi(deThi:DeThi)
    {
        rep.updateDeThi(deThi)
    }
    fun updateSocre(int:Int){
        rep.updateScore(int)
    }
    fun getScore(){
        rep.getDataScore {
            scor.postValue(it)
        }
    }
}

