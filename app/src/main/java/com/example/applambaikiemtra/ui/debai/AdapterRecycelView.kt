package com.example.applambaikiemtra.ui.debai

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import kotlinx.android.synthetic.main.custom_cauhoi.view.*




interface OnItemClicks  {
    fun OnItemClick(v:View,position: Int)
}
class AdapterRecycelView(val list:List<String>,val int:Int) : RecyclerView.Adapter<AdapterRecycelView.viewHodel>() {

    var listener:OnItemClicks? = null
    fun setOnItemClick( listener: OnItemClicks)
    {
        this.listener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHodel {
        val view:View=View.inflate(parent.context,int,null)
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
        init {
            btnDeBai.setOnClickListener(this)
        }

    }

}