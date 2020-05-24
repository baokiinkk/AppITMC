package com.example.applambaikiemtra.data.repository

import android.util.Log
import com.example.applambaikiemtra.data.api.firestore
import com.example.applambaikiemtra.data.db.AppDao
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.BoMon
import com.example.applambaikiemtra.data.db.model.DeThi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(val data: firestore,val dao: AppDao) {
    //Bài thi

    // lấy data bài thi từ sqlite trên luồng IO. dùng con trỏ hàm để tầng dưới,tầng viewmodel nhận được dữ liệu
    fun loadDataBaiThi(idDeThi: String,x:(MutableList<BaiThi>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO){
            x(dao.getBaiThi(idDeThi))
    }
    }
    // tương tự. nhưng khác là chỉ đếm size dữ liệu
    fun getDataBaiThi(idDeThi: String,x:(Int)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO){
            x(dao.getBaiThi(idDeThi).size)
        }
    }
    // load dữ liệu trên firebase vào sqlite.
    fun loadDataBaiThiToSQL(boMon: String,deThi: String,x:(MutableList<BaiThi>)->Unit)
    {
        data.getBaiLam(boMon,deThi){ data->  // dùng con trỏ hàm mà ở tầng firestore đã dùng để nhận dx dữ liệu từ firebase
            GlobalScope.launch(Dispatchers.IO){// vào luồng IO
                for (x in data)
                {
                    // đưa dữ liệu vào sqlite, nếu tồn tại thì update các thuộc tính
                    dao.addBaiThi(BaiThi(x["Câu hỏi"]!!,x["A"]!!,x["B"]!!,x["C"]!!,x["D"]!!,x["Đáp án"]!!,deThi))
                    dao.updateBaiThi(BaiThi(x["Câu hỏi"]!!,x["A"]!!,x["B"]!!,x["C"]!!,x["D"]!!,x["Đáp án"]!!,deThi))
                }
                x(dao.getBaiThi(deThi))
            }

        }
    }

    //BoMon
     fun loadDataBoMon(x:(MutableList<BoMon>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO) {
            x(dao.getALLBoMon())
        }
    }
     fun loadDataBoMonToSQL(xx:(MutableList<BoMon>)->Unit)
    {
            data.getSizeBoMon {
                GlobalScope.launch(Dispatchers.IO) {
                    for(x in it.values.toMutableList())
                        dao.addBoMon(BoMon(x))
                    xx(dao.getALLBoMon())
                }

            }
    }
    //DeThi
    fun updateDeThi(deThi: DeThi)
    {
        GlobalScope.launch(Dispatchers.IO) {
            dao.updateDeThi(deThi)
        }
    }
    fun loadDataDeThi(boMon: String,x:(MutableList<DeThi>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO){
            x(dao.getDethi(boMon))
        }
    }
    fun loadDataDeThiToSQL(boMon: String)
    {
        data.getSizeDeBai(boMon){
            GlobalScope.launch(Dispatchers.IO){
                for(x in it){
                    data.getBaiLam(boMon,x.value){
                        GlobalScope.launch {
                            var list=""
                            for(i in 0.. it.size)
                                list+="0"
                            dao.addDeThi(DeThi(ten=x.value,bomon = boMon,socau = it.size,socaulamdung = -1,list = list,socausql = 0))
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
