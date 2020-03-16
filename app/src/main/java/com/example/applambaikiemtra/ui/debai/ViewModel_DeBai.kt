package com.example.applambaikiemtra.ui.debai

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.network.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ViewModel_DeBai(val firebase:firestore) :ViewModel() {
    var list = mutableListOf<String>()
    var toLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
    var tocheck: MutableLiveData<Boolean?> = MutableLiveData(null)
    var test:String="Môn Toán"
    fun loadData(bomon:String)
    {
        viewModelScope.launch {
            var getdata = firebase.getSizeDeBai(bomon)
            var flow: Long? = getdata["Số lượng đề"]
            var temp :Int = list.size
                if(temp < flow!!)
                {
                    for (i in temp..flow-1)
                        list.add("Đề"+(i+1))
                    tocheck.postValue(false)
                }
                else if(temp>flow)
                {
                    for(i in flow..temp)
                        list.removeAt(list.size-1)
                    tocheck.postValue(true)
                }
            }

        }
    }
