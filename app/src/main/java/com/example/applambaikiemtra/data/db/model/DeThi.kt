package com.example.applambaikiemtra.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeThi(@PrimaryKey val ten:String, val bomon:String,
                 val socaulamdung:Int, var socau:Int, val list:String)