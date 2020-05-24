package com.example.applambaikiemtra.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Suppress("UNCHECKED_CAST")
class firestore {

    //  lấy tất cả bộ môn có trên firestore. dùng con trỏ hàm để tầng dưới tầng repository lấy được dữ liệu. thực hiện
    // thực hiện trên luồng IO luồng đọc ghi
    val root=FirebaseFirestore.getInstance()
     fun getSizeBoMon(onsuccess:(MutableMap<String,String>)->Unit) {
        root.collection("Tổng môn").document("3").get()
            .addOnSuccessListener {
               onsuccess(it.data as MutableMap<String, String>)
            }
    }

    // tương tự
     fun getSizeDeBai(bomon:String,onsuccess:(MutableMap<String,String>)->Unit){

        root.collection(bomon).document(bomon).get()
            .addOnSuccessListener {
                onsuccess(it.data as MutableMap<String, String>)
            }
            .addOnFailureListener{
            }

    }
    fun getBaiLam(bomon: String,Debai:String,onsuccess: (MutableList<MutableMap<String, String>>) -> Unit) {
        var list:MutableList<MutableMap<String,String>> = mutableListOf()
        root.collection(bomon).document(bomon).collection(Debai).get()
            .addOnSuccessListener {
                for(x in it)
                    list.add(x.data as MutableMap<String, String>)
                onsuccess(list)
            }
    }
}