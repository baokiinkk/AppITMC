package com.example.applambaikiemtra.ui.cauhoi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.repository.Repository
import kotlinx.coroutines.launch
class ViewModel_BaiThi(val rep:Repository) :ViewModel() {
    var list:MutableLiveData<MutableList<BaiThi> > = MutableLiveData()
    val DeBai:MutableLiveData<String>? = null
    var check:MutableLiveData<Boolean> = MutableLiveData(false)
    var text:MutableLiveData<String> = MutableLiveData("Thời Gian")
    var cauDung:MutableLiveData<Int> = MutableLiveData(0)
    var checkdapan:MutableLiveData<Boolean> = MutableLiveData(false)
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
}

