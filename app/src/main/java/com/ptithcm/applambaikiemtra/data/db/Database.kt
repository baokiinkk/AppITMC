package com.ptithcm.applambaikiemtra.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ptithcm.applambaikiemtra.data.db.model.BaiThi
import com.ptithcm.applambaikiemtra.data.db.model.BoMon
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.data.db.model.Score

@Database(entities = [BaiThi::class,BoMon::class,DeThi::class, Score::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appDao(): AppDao // bac buoc co

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // 3 công việc: check instance khác null thì return nó, null thì new ra + gán lại vào nó rồi return

            // full cú pháp kotlin
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

            //  kotlin 50%
//            return instance ?: synchronized(this) {
//               if (instance != null) {
//                   instance = buildDatabase(context)
//               }
//                instance!!
//            }

            // cách if bth java
//            if (instance == null) {
//                synchronized(this) {
//                    if (instance == null) {
//                        instance = buildDatabase(context)
//                    }
//                }
//            }
//            return instance!!

        }

        // ham khoi tao database room
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDB")
                .build()
        }
    }
}