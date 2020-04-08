package com.example.applambaikiemtra.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.BoMon
import com.example.applambaikiemtra.data.db.model.DeThi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(val data: firestore,val dao: AppDao) {
    //Bài thi
    fun updateBaiThi(baiThi: BaiThi)
    {
        GlobalScope.launch {
            dao.updateBaiThi(baiThi)
        }
    }
    fun loadDataBaiThi(idDeThi: Int,x:(MutableList<BaiThi>)->Unit)
    {
        GlobalScope.launch {
            x(dao.getBaiThi(idDeThi))
        }
    }
    fun loadDataBaiThiToSQL(boMon: String,deThi: String,idDeThi: Int)
    {
        data.getBaiLam(boMon,deThi){ data->
            GlobalScope.launch {
                for (x in data)
                {
                    dao.addBaiThi(BaiThi(x["Câu hỏi"]!!,x["A"]!!,x["B"]!!,x["C"]!!,x["D"]!!,x["Đáp án"]!!,idDeThi))
                }
            }

        }
    }
    fun getSizeBaiThiOnline(boMon: String,deThi: String,x:(Int)->Unit)
    {
        data.getBaiLam(boMon,deThi){
            x(it.size)
        }
    }

    //BoMon
     fun loadDataBoMon(x:(MutableList<BoMon>)->Unit)
    {
        GlobalScope.launch {
            x(dao.getALLBoMon())
        }
    }
     fun loadDataBoMonToSQL(xx:(MutableList<BoMon>)->Unit)
    {
            data.getSizeBoMon {
                val x=it.values.toMutableList()
                GlobalScope.launch {
                    val y:MutableList<BoMon> = dao.getALLBoMon()
                    if(x.size != y.size)
                    {
                        if(x.size > y.size)
                            for(i in y.size..x.size-1)
                            {
                                dao.addBoMon(BoMon(x[i]))
                            }
                    }
                    xx(dao.getALLBoMon())
                }

            }
    }
    //DeThi
    fun updateDeThi(deThi: DeThi)
    {
        GlobalScope.launch {
            dao.updateDeThi(deThi)
        }
    }
    fun loadDataDeThi(boMon: String,x:(MutableList<DeThi>)->Unit)
    {
        GlobalScope.launch {
            x(dao.getDethi(boMon))
        }
    }
    fun loadDataDeThiToSQL(boMon: String)
    {
        data.getSizeDeBai(boMon){
            val x=it.values.toMutableList()

            GlobalScope.launch {
                val y:MutableList<DeThi> = dao.getDethi(boMon)
                if(x.size != y.size)
                {
                    if(x.size > y.size)
                        for(i in y.size..x.size-1)
                        {
                            data.getBaiLam(boMon,x[i]){
                                GlobalScope.launch {
                                    dao.addDeThi(DeThi(ten=x[i],bomon = boMon,isDown = false,socau = it.size,socaulamdung = 0))
                                }
                            }

                        }
                }
            }

        }
    }
}
