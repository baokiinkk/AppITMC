package com.ptithcm.applambaikiemtra.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BaiThi(@PrimaryKey(autoGenerate = true) val id:Int , val cauhoi:String,val A:String,val B:String,val C:String,val D:String,val dapan:String,val DeThiID:String)