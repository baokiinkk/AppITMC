package com.example.applambaikiemtra.ui.cauhoi

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.ui.debai.AdapterRecycelView
import kotlinx.android.synthetic.main.custom_bai_thi.view.*

class ViewHodel(v: View):RecyclerView.ViewHolder(v)
{
    val txtCau=v.txtCau
    val txtCauHoi=v.txtCauHoi
    val btnA=v.A
    val btnB=v.B
    val btnC=v.C
    val btnD=v.D
}
class AdapterRecycleView(val list:MutableLiveData<MutableList<MutableMap<String,String>> >) :RecyclerView.Adapter<ViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodel {
        val view =View.inflate(parent.context, R.layout.custom_bai_thi,null)
        val viewHodel=ViewHodel(view)
        return viewHodel
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.txtCau.text="Câu "+(position+1)+" "
        holder.txtCauHoi.text= list.value?.get(position)?.get("Câu hỏi")

        holder.btnA.isChecked=false


        holder.btnA.text="A: "+ list.value?.get(position)?.get("A")
        holder.btnB.text="B: "+ list.value?.get(position)?.get("B")
        holder.btnC.text="C: "+ list.value?.get(position)?.get("C")
        holder.btnD.text="D: "+ list.value?.get(position)?.get("D")

    }
}