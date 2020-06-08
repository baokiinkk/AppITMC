package com.ptithcm.applambaikiemtra.data.repository

import com.ptithcm.applambaikiemtra.data.api.firestore
import com.ptithcm.applambaikiemtra.data.db.AppDao
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.BoMon
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
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
                dao.deleteBaiThi()
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
    fun getDatatoFireStore(boMon: String,xx: (MutableList<String>) -> Unit){
        data.getDeBai(boMon){
            xx(it.values.toMutableList())
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
                for(x in it){
                    data.getCauHoi(boMon,x.value){
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
                xx(dao.getDethi(boMon))
            }

        }
    }
}
