package com.example.applambaikiemtra.data.db

import androidx.room.*
import com.example.applambaikiemtra.data.db.model.BaiThi
import com.example.applambaikiemtra.data.db.model.BoMon
import com.example.applambaikiemtra.data.db.model.DeThi

@Dao
interface AppDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addBoMon(bomon: BoMon)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addDeThi(deThi: DeThi)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addBaiThi(baiThi: BaiThi)

    // delete
    @Delete
       suspend fun deleteDeThi(vararg deThi: DeThi)
    // update
    @Update
    suspend fun updateBoMon(bomon: BoMon)

    @Update
    suspend fun updateBaiThi(baiThi: BaiThi)

    @Update
    suspend fun updateDeThi(deThi: DeThi)

    //query
    @Query("select * from BoMon")
    suspend fun getALLBoMon(): MutableList<BoMon>

    @Query("select * from DeThi Where DeThi.bomon= :bomon")
    suspend fun getDethi(bomon:String): MutableList<DeThi>

    @Query("select * from DeThi Where DeThi.ten=:bomon")
    suspend fun getDethiS(bomon:String): DeThi

    @Query("select * from BaiThi Where BaiThi.DeThiID=:idDeThi")
    suspend fun getBaiThi(idDeThi: String): MutableList<BaiThi>

}