package com.example.applambaikiemtra.ui.boMon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applambaikiemtra.data.db.model.BoMon
import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.repository.Repository
import kotlinx.coroutines.launch

class ViewModel_BoMon(val repo:Repository): ViewModel() {

        var list:MutableLiveData<MutableList<BoMon>> = MutableLiveData()
        var toLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
        var tocheck: MutableLiveData<Boolean?> = MutableLiveData(null)
        var test:MutableLiveData<String> = MutableLiveData()
         fun listener()
        {
            toLogin.postValue(true)
        }
         fun loadData()
        {
           repo.loadDataBoMon {
               list.postValue(it)
           }

        }
        fun loadDatatoSQl()
        {
            repo.loadDataBoMonToSQL{
                list.postValue(it)
            }
        }
    }
