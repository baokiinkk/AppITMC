package com.example.applambaikiemtra.ui.boMon

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.network.firestore
import kotlinx.coroutines.launch

class ViewModel_BoMon(val firebase:firestore): ViewModel() {

        var list:MutableLiveData<MutableMap<String,String>> = MutableLiveData()
        var toLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
        var tocheck: MutableLiveData<Boolean?> = MutableLiveData(null)
        var test:String=""
        fun listener()
        {
            loadData()
            toLogin.postValue(true)
        }
        fun loadData()
        {
            viewModelScope.launch {
                list.postValue(firebase.getSizeBoMon())

            }

        }
    }
