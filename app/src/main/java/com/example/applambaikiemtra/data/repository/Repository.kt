package com.example.applambaikiemtra.data.repository

import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.BoMon
import com.example.applambaikiemtra.data.db.model.DeThi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(val data: firestore,val dao: AppDao) {
    //Bài thi

    fun loadDataBaiThi(idDeThi: String,x:(MutableList<BaiThi>)->Unit)
    {
        GlobalScope.launch {
            x(dao.getBaiThi(idDeThi))
        }
    }
    fun loadDataBaiThiToSQL(boMon: String,deThi: String,x:(MutableList<BaiThi>)->Unit)
    {
        data.getBaiLam(boMon,deThi){ data->
            GlobalScope.launch {
                for (x in data)
                {
                    dao.addBaiThi(BaiThi(x["Câu hỏi"]!!,x["A"]!!,x["B"]!!,x["C"]!!,x["D"]!!,x["Đáp án"]!!,deThi))
                    dao.updateBaiThi(BaiThi(x["Câu hỏi"]!!,x["A"]!!,x["B"]!!,x["C"]!!,x["D"]!!,x["Đáp án"]!!,deThi))
                }
                x(dao.getBaiThi(deThi))
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
                GlobalScope.launch {
                    for(x in it.values.toMutableList())
                        dao.addBoMon(BoMon(x))
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
            GlobalScope.launch {
                for(x in it){
                    data.getBaiLam(boMon,x.value){
                        GlobalScope.launch {
                            dao.addDeThi(DeThi(ten=x.value,bomon = boMon,socau = it.size,socaulamdung = 0,list = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000"))
                           val dethi:DeThi= dao.getDethiS(x.value)
                            dethi.socau=it.size
                            dao.updateDeThi(dethi)
                        }
                    }
                }
            }

        }
    }
}
