package com.ptithcm.applambaikiemtra.ui.debai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.data.db.model.DeThi
import com.ptithcm.applambaikiemtra.databinding.ItemDeThiBinding
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.item_de_thi.view.*


class DeBaiAdapter(val setBaseClick:((Int,Int)->Unit)) : ListAdapter<DeThi, DeBaiAdapter.ViewHodel>(DeBaiDiff()) {


    class ViewHodel(val binding:ItemDeThiBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemDeThiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:DeThi, baseClick:((Int, Int)->Unit)?=null)
        {
            binding.item=item
            binding.executePendingBindings()
            itemView.download.visibility = View.VISIBLE
            itemView.txtSoCau.visibility=View.VISIBLE
            itemView.txtSoCauLamDuoc.visibility=View.INVISIBLE
            itemView.btnThiLai.visibility=View.GONE
            if(item.socau <= 0)
                itemView.txtSoCau.visibility=View.INVISIBLE
            else{
                var x=item.socaulamdung*100/ item.socau
                if(x <= 0) x=1
                itemView.progressbar.progress=x
                itemView.txtSoCau.text= item.socau.toString() +" câu"
                if(item.bomon != "Vật lí 3")
                    itemView.txtThoiGian.text = (item.socau).toString()+" phút"
                else
                    itemView.txtThoiGian.text = ((item.socau*50*3)/60 - 10).toString()+" phút"
            }
            if(item.socaulamdung == -1) {
                itemView.txtSoCauLamDuoc.visibility=View.INVISIBLE
                itemView.btnThiLai.visibility=View.GONE
            }
            else
            {
                itemView.txtSoCauLamDuoc.visibility=View.VISIBLE
                itemView.txtSoCauLamDuoc.text = item.socaulamdung.toString()+" đúng"
                itemView.btnThiLai.visibility=View.VISIBLE
            }
            if(item.socausql != -1)
                itemView.download.visibility =View.INVISIBLE
            baseClick?.let {click->
                itemView.setOnClickListener{
                    click(adapterPosition,1)
                }
                itemView.btnThiLai.setOnClickListener {
                    click(adapterPosition,3)
                }
                itemView.download.setOnClickListener {

                    click(adapterPosition,2)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeBaiAdapter.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: DeBaiAdapter.ViewHodel, position: Int) {
        holder.bind(getItem(position),setBaseClick)
    }
}

class DeBaiDiff: DiffUtil.ItemCallback<DeThi>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: DeThi, newItem: DeThi): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.ten == newItem.ten // dung
}

    override fun areContentsTheSame(oldItem: DeThi, newItem: DeThi): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}