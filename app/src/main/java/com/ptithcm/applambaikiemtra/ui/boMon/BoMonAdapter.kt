package com.ptithcm.applambaikiemtra.ui.boMon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptithcm.applambaikiemtra.data.db.model.BoMon
import com.ptithcm.applambaikiemtra.databinding.ItemBoMonBinding

class BoMonAdapter(val setBaseClick:((Int)->Unit)) :ListAdapter<BoMon,BoMonAdapter.ViewHodel>(MyDIff()) {

    class ViewHodel(val binding:ItemBoMonBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemBoMonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
            fun bind(item:BoMon,baseClick:((Int)->Unit)?=null)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoMonAdapter.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: BoMonAdapter.ViewHodel, position: Int) {
            holder.bind(getItem(position),setBaseClick)
    }
}

class MyDIff: DiffUtil.ItemCallback<BoMon>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: BoMon, newItem: BoMon): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.tenBoMon == newItem.tenBoMon // dung
}

    override fun areContentsTheSame(oldItem: BoMon, newItem: BoMon): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}