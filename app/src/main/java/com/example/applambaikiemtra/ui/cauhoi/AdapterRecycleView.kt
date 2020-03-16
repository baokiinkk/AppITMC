package com.example.applambaikiemtra.ui.cauhoi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import kotlinx.android.synthetic.main.custom_bai_thi.view.*
import java.util.*


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
        val view =LayoutInflater.from(parent.context).inflate( R.layout.custom_bai_thi,parent,false)
        val viewHodel=ViewHodel(view)
        return viewHodel
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.txtCau.text="Câu "+(position+1)+" "
        holder.txtCauHoi.text= list.value?.get(position)?.get("Câu hỏi")

        holder.btnA.text="A: "+ list.value?.get(position)?.get("A")
        holder.btnB.text="B: "+ list.value?.get(position)?.get("B")
        holder.btnC.text="C: "+ list.value?.get(position)?.get("C")
        holder.btnD.text="D: "+ list.value?.get(position)?.get("D")

            Toast.makeText(holder.btnA.context,position.toString(),Toast.LENGTH_SHORT).show()
            var dapan= list.value?.get(position)?.get("Đáp án")
            check(holder.btnA,holder.btnB,holder.btnC,holder.btnD,dapan)
            check(holder.btnB,holder.btnA,holder.btnC,holder.btnD,dapan)
            check(holder.btnC,holder.btnA,holder.btnB,holder.btnD,dapan)
            check(holder.btnD,holder.btnA,holder.btnB,holder.btnC,dapan)



    }
    fun check(button: RadioButton,buttoA: RadioButton,buttoB: RadioButton,buttoC: RadioButton,test:String?)
    {
        val temp:String= button.text.toString().removeRange(0,3)
            if(button.isChecked == true && temp != test)
            {
                button.setBackgroundResource(R.drawable.err)
                button.isEnabled=false
                checkOK(buttoA,buttoB,buttoC,test)
            }
            if(button.isChecked == true && temp == test)
            {
                button.isEnabled=false
                buttoA.isEnabled=false
                buttoB.isEnabled=false
                buttoC.isEnabled=false

            }

    }
    fun checkOK(btnA:RadioButton,btnB:RadioButton,btnC:RadioButton,test: String?)
    {
        btnA.isEnabled=false
        btnB.isEnabled=false
        btnC.isEnabled=false
        val tempA:String= btnA.text.toString().removeRange(0,3)
        val tempB:String= btnB.text.toString().removeRange(0,3)
        val tempC:String= btnC.text.toString().removeRange(0,3)

        if(tempA == test)
            btnA.setBackgroundResource(R.drawable.checked)
        else if(tempB == test)
            btnB.setBackgroundResource(R.drawable.checked)
        else
            btnC.setBackgroundResource(R.drawable.checked)
    }
}