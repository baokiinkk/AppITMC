package com.ptithcm.applambaikiemtra.data.repository

import android.os.SystemClock
import android.util.Log
import com.ptithcm.applambaikiemtra.data.api.firestore
import com.ptithcm.applambaikiemtra.data.db.AppDao
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.BoMon
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.db.model.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(val data: firestore,val dao: AppDao) {
    //Bài thi

    // lấy data bài thi từ sqlite trên luồng IO. dùng con trỏ hàm để tầng dưới,tầng viewmodel nhận được dữ liệu
    fun getDataCauHoiFromSQL(idDeThi: String, x:(MutableList<BaiThi>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO){
            x(dao.getBaiThi(idDeThi))
    }
    }

    // load dữ liệu trên firebase vào sqlite.
    fun getDataCauHoiFromApiToSQL(boMon: String, deThi: String, x:(MutableList<BaiThi>)->Unit)
    {
        data.getCauHoi(boMon,deThi){ data->  // dùng con trỏ hàm mà ở tầng firestore đã dùng để nhận dx dữ liệu từ firebase

            GlobalScope.launch(Dispatchers.IO){// vào luồng IO
                for (x in data)
                {
                    // đưa dữ liệu vào sqlite, nếu tồn tại thì update các thuộc tính
                    dao.addBaiThi(BaiThi(cauhoi = x["Câu hỏi"]!!,A = x["A"]!!,B = x["B"]!!,C = x["C"]!!,D = x["D"]!!,dapan = x["Đáp án"]!!,DeThiID = deThi,id = 0))
                }
                val x=dao.getBaiThi(deThi)
                Log.d("mmmmmmmmmmmmmmm",x.size.toString())
                x(x)
            }

        }
    }

    //BoMon
     fun getDataBoMonFromSQL(x:(MutableList<BoMon>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO) {
            x(dao.getALLBoMon())
        }
    }
     fun getDataBoMonFromApiToSQL(xx:(MutableList<BoMon>)->Unit)
    {
            data.getBoMon {
                GlobalScope.launch(Dispatchers.IO) {
                    dao.deleteBoMon()
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
    fun getDataDeThiFromSQL(boMon: String, x:(MutableList<DeThi>)->Unit)
    {
        GlobalScope.launch(Dispatchers.IO){
            x(dao.getDethi(boMon))
        }
    }
    fun getDataDeThiFromApiToSQL(boMon: String,xx:(MutableList<DeThi>)->Unit)
    {
        data.getDeBai(boMon){
            GlobalScope.launch(Dispatchers.IO){
                for(x in it.values.toMutableList()){
                    dao.addDeThi(DeThi(x,boMon,-1,-1,-1,""))
                }
                xx(dao.getDethi(boMon))
            }
        }
    }

    fun getDataScore(xx:(Int)->Unit){

        GlobalScope.launch(Dispatchers.IO) {
            if(dao.getScore() == null)
                dao.addScore(Score(10))
            xx(dao.getScore().score)
        }
    }
    fun updateScore(int:Int){
        GlobalScope.launch (Dispatchers.IO){
            dao.deleteScore()
            dao.addScore(Score(int))
        }
    }
}

