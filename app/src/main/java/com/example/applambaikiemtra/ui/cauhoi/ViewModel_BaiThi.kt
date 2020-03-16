package com.example.applambaikiemtra.ui.cauhoi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.network.firestore
import kotlinx.coroutines.launch
class ViewModel_BaiThi(val firestore: firestore) :ViewModel() {
    var list:MutableLiveData<MutableList<MutableMap<String,String>> > = MutableLiveData()
    val DeBai:MutableLiveData<String>? = null
    var check:MutableLiveData<Boolean> = MutableLiveData(false)
    var text:MutableLiveData<String> = MutableLiveData("Thời Gian")
    var cauDung:MutableLiveData<String> = MutableLiveData("0")
    fun listener()
    {
        check.postValue(true)
    }
    fun getData(bomon:String,debai:String)
    {
        viewModelScope.launch {
               list.postValue(firestore.getBaiLam(bomon,debai))
        }
    }
}

