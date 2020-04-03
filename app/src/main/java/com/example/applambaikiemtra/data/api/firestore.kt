package com.example.applambaikiemtra.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Suppress("UNCHECKED_CAST")
class firestore {
    val root=FirebaseFirestore.getInstance()
     fun getSizeBoMon(onsuccess:(MutableMap<String,String>)->Unit) {
        root.collection("Tổng môn").document("3").get()
            .addOnSuccessListener {
               onsuccess(it.data as MutableMap<String, String>)
            }

    }

     fun getSizeDeBai(bomon:String,onsuccess:(MutableMap<String,String>)->Unit){

        root.collection(bomon).document(bomon).get()
            .addOnSuccessListener {
                onsuccess(it.data as MutableMap<String, String>)
            }
            .addOnFailureListener{
            }

    }
    suspend fun getBaiLam(bomon: String,Debai:String) = suspendCancellableCoroutine<MutableList<MutableMap<String,String>>> { cont->
        var list:MutableList<MutableMap<String,String>> = mutableListOf()
        root.collection(bomon).document(bomon).collection(Debai).get()
            .addOnSuccessListener {
                for(x in it)
                    list.add(x.data as MutableMap<String, String>)
                cont.resume(list)
            }
    }

    fun takeOffine()
    {
        val setting= FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
        root.firestoreSettings=setting
    }


}