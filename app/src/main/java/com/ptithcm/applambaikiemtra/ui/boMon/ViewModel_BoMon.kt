package com.ptithcm.applambaikiemtra.ui.boMon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ptithcm.applambaikiemtra.data.db.model.BoMon
import com.ptithcm.applambaikiemtra.data.repository.Repository
class ViewModel_BoMon(val repo:Repository): ViewModel() {

        var list:MutableLiveData<MutableList<BoMon>> = MutableLiveData()
        var toLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
        var test:MutableLiveData<String> = MutableLiveData()
         fun listener()
        {
            toLogin.postValue(true)
        }
         fun loadData()
        {
           repo.getDataBoMonFromSQL {
               list.postValue(it)
           }

        }
        fun loadDatatoSQl()
        {
            repo.getDataBoMonFromApiToSQL{
                list.postValue(it)
            }
        }

    }
