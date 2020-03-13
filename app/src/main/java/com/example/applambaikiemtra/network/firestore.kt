package com.example.applambaikiemtra.network

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Suppress("UNCHECKED_CAST")
class firestore {
    suspend fun getSizeBoMon() = suspendCancellableCoroutine<MutableMap<String,String>> {cont->
        val root=FirebaseFirestore.getInstance()
        root.collection("Tổng môn").document("3").get()
            .addOnSuccessListener {
               cont.resume(it.data as MutableMap<String, String>)
            }

    }

    suspend fun getSizeDeBai(bomon:String)= suspendCancellableCoroutine<HashMap<String,Long>> {cont->

        val root= FirebaseFirestore.getInstance()
        root.collection(bomon).document(bomon).get()
            .addOnSuccessListener {
                cont.resume(it.data as HashMap<String, Long>)
            }
            .addOnFailureListener{
            }

    }
    suspend fun getBaiLam(bomon: String,Debai:String) = suspendCancellableCoroutine<MutableList<MutableMap<String,String>>> { cont->
        var list:MutableList<MutableMap<String,String>> = mutableListOf()
        val root=FirebaseFirestore.getInstance()
        root.collection(bomon).document(bomon).collection(Debai).get()
            .addOnSuccessListener {
                for(x in it)
                    list.add(x.data as MutableMap<String, String>)
                cont.resume(list)
            }
    }
    suspend fun data() = suspendCancellableCoroutine<String> {

    }



}