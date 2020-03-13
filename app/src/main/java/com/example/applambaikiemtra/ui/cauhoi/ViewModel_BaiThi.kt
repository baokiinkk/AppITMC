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
    fun listener()
    {

    }
    fun getData(debai:String)
    {
        viewModelScope.launch {
               list.postValue(firestore.getBaiLam("Toán",debai))
        }
    }
}

