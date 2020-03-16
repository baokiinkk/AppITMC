package com.example.applambaikiemtra.ui.boMon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import kotlinx.android.synthetic.main.custom_bo_mon.view.*
import kotlinx.android.synthetic.main.custom_cauhoi.view.*
import kotlinx.android.synthetic.main.custom_cauhoi.view.textView


interface OnItemClicks  {
    fun OnItemClick(v:View,position: Int)
}
class AdapterRecycelView(val list:List<String>) : RecyclerView.Adapter<AdapterRecycelView.viewHodel>() {

    var listener:OnItemClicks? = null
    fun setOnItemClick( listener: OnItemClicks)
    {
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHodel {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_bo_mon ,parent,false)
        var viewHodel:viewHodel= viewHodel(view)
        return viewHodel
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHodel, position: Int) {
        holder.btnDeBai.text=list[position]
    }
    inner class viewHodel(v:View): RecyclerView.ViewHolder(v),View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v != null) {
                listener?.OnItemClick(v,adapterPosition)
            }
        }

        val btnDeBai=v.textView
        val hinhanh=v.imageView2
        init {
            hinhanh.setOnClickListener(this)
        }

    }

}