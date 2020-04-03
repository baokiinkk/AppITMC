package com.example.applambaikiemtra.ui.debai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.databinding.CustomCauhoiBinding
import com.example.applambaikiemtra.data.db.model.DeThi


class DeBaiAdapter(val setBaseClick:((Int)->Unit)) : ListAdapter<DeThi, DeBaiAdapter.ViewHodel>(DeBaiDiff()) {


    class ViewHodel(val binding:CustomCauhoiBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    CustomCauhoiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:DeThi,baseClick:((Int)->Unit)?=null)
        {
            binding.item=item
            binding.executePendingBindings()
            baseClick?.let {click->
                itemView.setOnClickListener{
                    click(adapterPosition)
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