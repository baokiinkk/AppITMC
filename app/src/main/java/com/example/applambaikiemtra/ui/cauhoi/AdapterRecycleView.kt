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
    val btnA=v.btnA
    val btnB=v.btnB
    val btnC=v.btnC
    val btnD=v.btnD
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

        holder.btnA.textOff="A: "+ list.value?.get(position)?.get("A")
        holder.btnB.textOff="B: "+ list.value?.get(position)?.get("B")
        holder.btnC.textOff="C: "+ list.value?.get(position)?.get("C")
        holder.btnD.textOff="D: "+ list.value?.get(position)?.get("D")

        holder.btnA.textOn="A: "+ list.value?.get(position)?.get("A")
        holder.btnB.textOn="B: "+ list.value?.get(position)?.get("B")
        holder.btnC.textOn="C: "+ list.value?.get(position)?.get("C")
        holder.btnD.textOn="D: "+ list.value?.get(position)?.get("D")

    }
}