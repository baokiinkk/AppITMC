package com.example.applambaikiemtra.ui.cauhoi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.data.db.model.BaiThi
import kotlinx.android.synthetic.main.custom_bai_thi.view.*
import kotlinx.android.synthetic.main.custom_bai_thi.view.A
import kotlinx.android.synthetic.main.custom_bai_thi.view.B
import kotlinx.android.synthetic.main.custom_bai_thi.view.C
import kotlinx.android.synthetic.main.custom_bai_thi.view.D
import kotlinx.android.synthetic.main.custom_bai_thi.view.txtCau
import kotlinx.android.synthetic.main.custom_bai_thi.view.txtCauHoi

import java.util.jar.Attributes


class AdapterRecycleView(val list:MutableLiveData<MutableList<BaiThi>>,listchon:String,val x:(Int)->Unit) :RecyclerView.Adapter<AdapterRecycleView.ViewHodel>() {
    var boolean: Boolean=false

    val listLuuVitri:MutableList<Char> = mutableListOf()
    init {
        for(i in 0.. list.value!!.size-1) {
            listLuuVitri.add(listchon[i])
        }
    }

    inner class ViewHodel(v: View):RecyclerView.ViewHolder(v),View.OnClickListener
    {
        override fun onClick(v: View?) {
            when(v?.id)
            {
                btnA.id-> {
                    listLuuVitri[adapterPosition] = '1'
                    x(adapterPosition)
                }
                btnB.id->
                {
                    listLuuVitri[adapterPosition]='2'
                    x(adapterPosition)
                }
                btnC.id->
                {
                    listLuuVitri[adapterPosition]='3'
                    x(adapterPosition)
                }
                btnD.id->
                {
                    listLuuVitri[adapterPosition]='4'
                    x(adapterPosition)
                }
            }
        }

        val txtCau=v.txtCau
        val txtCauHoi=v.txtCauHoi
        val btnA=v.A
        val btnB=v.B
        val btnC=v.C
        val btnD=v.D
        val group=v.group
        init {
            btnA.setOnClickListener(this)
            btnB.setOnClickListener(this)
            btnC.setOnClickListener(this)
            btnD.setOnClickListener(this)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodel {
        val view =LayoutInflater.from(parent.context).inflate( R.layout.custom_bai_thi,parent,false)
        val viewHodel=ViewHodel(view)
        if(viewHodel.group == null)
            Log.i("aaaaaaaaaa","null")
        return viewHodel
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.txtCau.text="Câu "+(position+1)+" "
        holder.txtCauHoi.text= list.value?.get(position)?.cauhoi
        val A =list.value?.get(position)?.A
        val B =list.value?.get(position)?.B
        val C =list.value?.get(position)?.C
        val D =list.value?.get(position)?.D
        holder.btnA.text="A: "+ A
        holder.btnB.text="B: "+ B
        holder.btnC.text="C: "+ C
        holder.btnD.text="D: "+ D



        var vitriDapAn= list.value?.get(position)?.dapan

        if(listLuuVitri[position] == '1')
            holder.btnA.isChecked=true
        else if(listLuuVitri[position] == '2')
            holder.btnB.isChecked=true
        else if(listLuuVitri[position]== '3')
            holder.btnC.isChecked=true
        else if(listLuuVitri[position] == '4')
            holder.btnD.isChecked=true
        else
        {
            holder.group.clearCheck()
        }

        if(boolean ==true)
        {
            check(holder.btnA,holder.btnB,holder.btnC,holder.btnD, vitriDapAn!!,listLuuVitri[position])
        }

    }

    fun reset(btnA: RadioButton,btnB: RadioButton,btnC: RadioButton,btcD:RadioButton)
    {
        btnA.setBackgroundResource(R.drawable.nocheck)
        btnB.setBackgroundResource(R.drawable.nocheck)
        btnC.setBackgroundResource(R.drawable.nocheck)
        btcD.setBackgroundResource(R.drawable.nocheck)

        btnA.isEnabled=false
        btnB.isEnabled=false
        btnC.isEnabled=false
        btcD.isEnabled=false
    }
    fun toXanh(vitri:String,btnA: RadioButton,btnB: RadioButton,btnC: RadioButton,btcD: RadioButton)
    {
        if(vitri == "1")
        {
            btnA.setBackgroundResource(R.drawable.checked)

        }
        else if(vitri == "2")
        {
            btnB.setBackgroundResource(R.drawable.checked)
        }
        else if(vitri == "3"){
            btnC.setBackgroundResource(R.drawable.checked)
        }
        else if(vitri == "4")
        {
            btcD.setBackgroundResource(R.drawable.checked)
        }
    }
    fun toDo(vitri:Char,btnA: RadioButton,btnB: RadioButton,btnC: RadioButton,btcD: RadioButton)
    {
        if(vitri == '1') btnA.setBackgroundResource(R.drawable.err)
        else if(vitri == '2') btnB.setBackgroundResource(R.drawable.err)
        else if(vitri == '3') btnC.setBackgroundResource(R.drawable.err)
        else if(vitri == '4')  btcD.setBackgroundResource(R.drawable.err)
    }
    fun check(btnA:RadioButton,btnB:RadioButton,btnC:RadioButton,btcD: RadioButton,dapan:String,luachon:Char)
    {
        reset(btnA,btnB,btnC,btcD)
        if(dapan == luachon.toString() || luachon == '0')
        {
            toXanh(dapan,btnA,btnB,btnC,btcD)
        }
        else
        {
            toDo(luachon,btnA,btnB,btnC,btcD)
            toXanh(dapan,btnA,btnB,btnC,btcD)
        }
    }
}

