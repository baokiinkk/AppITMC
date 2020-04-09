package com.example.applambaikiemtra.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeThi(@PrimaryKey(autoGenerate = true) val id:Int=0, val ten:String, val bomon:String,
                 var isDown:Boolean,val socaulamdung:Int,val socau:Int,val list:String)