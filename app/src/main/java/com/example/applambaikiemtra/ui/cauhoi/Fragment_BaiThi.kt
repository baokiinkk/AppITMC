package com.example.applambaikiemtra.ui.cauhoi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applambaikiemtra.R
import com.example.applambaikiemtra.databinding.FragmentBaiThiBinding
import kotlinx.android.synthetic.main.custom_bai_thi.*
import kotlinx.android.synthetic.main.fragment__bai_thi.*
import kotlinx.android.synthetic.main.fragment__bai_thi.view.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class Fragment_BaiThi : Fragment() {
    val viewmodel:ViewModel_BaiThi by viewModel<ViewModel_BaiThi>()
    val args :Fragment_BaiThiArgs by navArgs()
    lateinit var adapterRecycelView: AdapterRecycleView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bd:FragmentBaiThiBinding=DataBindingUtil.inflate(inflater,R.layout.fragment__bai_thi,container,false)
        bd.lifecycleOwner = this
        bd.viewmodel=viewmodel
        getData()
        viewmodel.list.observe(viewLifecycleOwner, Observer {
            if(it == null)
                Toast.makeText(context,"Rong",Toast.LENGTH_SHORT).show()
            else
            {
                adapterRecycelView=  AdapterRecycleView(viewmodel.list)
                val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)
                bd.recyclerView.adapter=adapterRecycelView
                bd.recyclerView.layoutManager=linearLayout
            }
        })




        return bd.root
    }
    fun getData()
    {
        viewmodel.getData(args.mon,args.ten)
    }

}
